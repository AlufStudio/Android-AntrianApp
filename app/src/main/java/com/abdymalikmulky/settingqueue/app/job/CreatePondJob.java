package com.abdymalikmulky.settingqueue.app.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.abdymalikmulky.settingqueue.app.data.pond.Pond;
import com.abdymalikmulky.settingqueue.app.data.pond.PondDataSource;
import com.abdymalikmulky.settingqueue.app.data.pond.PondLocal;
import com.abdymalikmulky.settingqueue.app.data.pond.PondRemote;
import com.abdymalikmulky.settingqueue.app.event.CreatingPondEvent;
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

    private Pond pond;

    public CreatePondJob(Pond pond) {
        super(new Params(Priority.HIGH).requireNetwork().persist().groupBy(Group.SAVE_POND));
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
            public void onFailed(String msg) {
                Timber.d("Kegagalan | %s ", pond.toString());
            }
        });
    }

    @Override
    public void onRun() throws Throwable {
        new PondRemote().save(pond, new PondDataSource.SavePondCallback() {
            @Override
            public void onSaved(Pond pond) {
                Timber.d("OnRunRemote | %s ", pond.toString());
                new PondLocal().updateSyncState(pond, new PondDataSource.SavePondCallback() {
                    @Override
                    public void onSaved(Pond pond) {
                        Timber.d("OnRunRemoteUpdate | %s ", pond.toString());
                        EventBus.getDefault().post(new CreatingPondEvent(pond));

                    }

                    @Override
                    public void onFailed(String msg) {
                        Timber.d("Kegagalan2 | %s ", msg);

                    }
                });

            }

            @Override
            public void onFailed(String msg) {
                Timber.d("Kegagalan1 | %s ", msg);

            }
        });
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        //terjadi kesalahan
        Timber.d("onCancel | JobJob %s", throwable.toString());
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        //retry methodnya ini
        Timber.d("OnReRun | JobJob %s", throwable.toString());
        return null;
    }
}
