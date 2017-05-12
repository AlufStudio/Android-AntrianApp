package com.abdymalikmulky.settingqueue.app.jobs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.abdymalikmulky.settingqueue.app.data.pond.Pond;
import com.abdymalikmulky.settingqueue.app.data.pond.PondDataSource;
import com.abdymalikmulky.settingqueue.app.data.pond.PondLocal;
import com.abdymalikmulky.settingqueue.app.data.pond.PondRemote;
import com.abdymalikmulky.settingqueue.app.events.pond.CreatingPondEvent;
import com.abdymalikmulky.settingqueue.app.events.pond.DeletedPondEvent;
import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

import org.greenrobot.eventbus.EventBus;

import timber.log.Timber;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 5/3/17.
 */

//Dibutuhkannya dagger agar hidup menjadi menyenangkan
public class CreatePondJob extends Job{

    private CreatePondJob INSTANCE;

    private Pond pond;

    public CreatePondJob(Pond pond) {
        super(new Params(Priority.HIGH).requireNetwork().persist().groupBy(Group.POST_JOB));
        this.pond = pond;
    }

    @Override
    public void onAdded() {
        new PondLocal().save(pond, new PondDataSource.SavePondCallback() {
            @Override
            public void onSaved(Pond pond) {
                Timber.d("onAdded | %s ", pond.toString());
                EventBus.getDefault().post(new CreatingPondEvent(pond));
            }
            @Override
            public void onFailed(Throwable t) {
                // TODO :: LOG yang enak kontekstual
                Timber.d("Kegagalan | %s ", pond.toString());
            }
        });
    }

    @Override
    public void onRun() throws Throwable {
        new PondRemote().save(pond, new PondDataSource.SaveRemotePondCallback() {
            @Override
            public void onSaved(Pond pond) {
                Timber.d("OnRunRemote | %s ", pond.toString());
                new PondLocal().update(pond, new PondDataSource.SavePondCallback() {
                    @Override
                    public void onSaved(Pond pond) {
                        Timber.d("OnRunRemoteUpdate | %s ", pond.toString());
                        EventBus.getDefault().post(new CreatingPondEvent(pond));
                    }
                    @Override
                    public void onFailed(Throwable t) {
                        Timber.d("OnRunFail | %s ", t.toString());
                    }
                });
            }
            @Override
            public void onFailed(Throwable t) throws Throwable {
                throw t;
            }
        });
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        new PondLocal().delete(pond);
        EventBus.getDefault().post(new DeletedPondEvent(pond));
        Timber.d("onCancel | JobJob %s", throwable.toString());
    }


    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        //retry methodnya ini
        Timber.d("OnReRun %s", pond.toString());
        Timber.d("OnReRun Run Count %s", runCount);
        Timber.d("OnReRun Run Max Count %s", maxRunCount);

        if (shouldRetry(throwable)) {
            //TODO :: waktu yang gradual
            RetryConstraint constraint = RetryConstraint
                    .createExponentialBackoff(runCount, 1000); //1 detik sajah
            constraint.setApplyNewDelayToGroup(true);

            return constraint;
        }
        return RetryConstraint.CANCEL;
    }


    @Override
    protected int getRetryLimit() {
        return 3;
    }


    private boolean shouldRetry(Throwable throwable) {
        if (throwable instanceof NetworkException) {
            NetworkException exception = (NetworkException) throwable;
            return exception.shouldRetry();
        }
        return true;
    }
}
