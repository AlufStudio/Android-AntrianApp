package com.abdymalikmulky.settingqueue.app.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.abdymalikmulky.settingqueue.app.data.setting.Setting;
import com.abdymalikmulky.settingqueue.app.data.setting.SettingDataSource;
import com.abdymalikmulky.settingqueue.app.data.setting.SettingLocal;
import com.abdymalikmulky.settingqueue.app.data.setting.SettingRemote;
import com.abdymalikmulky.settingqueue.app.event.setting.CreatingSettingEvent;
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
public class CreateSettingJob extends Job{

    private CreateSettingJob INSTANCE;

    private Setting setting;

    public CreateSettingJob(Setting setting) {
        super(new Params(Priority.HIGH).requireNetwork().persist().groupBy(Group.POST_JOB));
        this.setting = setting;
    }

    @Override
    public void onAdded() {
            new SettingLocal().save(setting, new SettingDataSource.SaveSettingCallback() {
                @Override
                public void onSaved(Setting setting) {
                    Timber.d("onAdded | %s ", setting.toString());
                    EventBus.getDefault().post(new CreatingSettingEvent(setting));
                }

                @Override
                public void onFailed(Throwable t) {
                    Timber.e("Fail-Local %s", t.toString());
                }
            });
    }
    @Override
    public void onRun() throws Throwable {
        Timber.d("onRun | %s", setting.toString());
        new SettingRemote().save(setting, new SettingDataSource.SaveRemoteSettingCallback() {
            @Override
            public void onSaved(Setting setting) {
                Timber.d("OnRunRemote | %s ", setting.toString());

            }

            @Override
            public void onFailed(Throwable t) throws Throwable {
                throw t;
            }
        });
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

        Timber.d("onCancel | JobJob %s", throwable.toString());
    }


    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        //retry methodnya ini
        Timber.d("OnReRun %s", setting.toString());
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
