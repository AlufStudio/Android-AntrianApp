package com.abdymalikmulky.settingqueue.app.jobs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.abdymalikmulky.settingqueue.app.data.setting.Setting;
import com.abdymalikmulky.settingqueue.app.data.setting.SettingDataSource;
import com.abdymalikmulky.settingqueue.app.data.setting.SettingLocal;
import com.abdymalikmulky.settingqueue.app.data.setting.SettingRemote;
import com.abdymalikmulky.settingqueue.app.events.setting.SettingCreatedSyncedEvent;
import com.abdymalikmulky.settingqueue.app.events.setting.SettingCreatedSyncingEvent;
import com.abdymalikmulky.settingqueue.app.jobs.util.Group;
import com.abdymalikmulky.settingqueue.app.jobs.util.NetworkException;
import com.abdymalikmulky.settingqueue.app.jobs.util.Priority;
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
                Timber.d("onAdded-Setting | %s ", setting.toString());
                EventBus.getDefault().post(new SettingCreatedSyncingEvent(setting));
            }

            @Override
            public void onFailed(Throwable t) {
                Timber.e("Fail-Local %s", t.toString());
            }
        });
    }
    @Override
    public void onRun() throws Throwable {
        Timber.d("onRun-Setting | %s", setting.toString());
        //updated pondId LOGS
        Setting newSetting = new SettingLocal().get(setting.getId());
        Timber.d("Data-Setting %s",setting.toString());
        Timber.d("Data-NewSetting %s",newSetting.toString());
        new SettingRemote().save(newSetting, new SettingDataSource.SaveRemoteSettingCallback() {
            @Override
            public void onSaved(Setting setting) {
                Timber.d("onRunRemote-Setting | %s ", setting.toString());
                new SettingLocal().updateSync(setting, new SettingDataSource.SaveSettingCallback() {
                    @Override
                    public void onSaved(Setting setting) {
                        Timber.d("onRunRemoteUpdate-Setting | %s ", setting.toString());
                        EventBus.getDefault().post(new SettingCreatedSyncedEvent(setting));
                    }

                    @Override
                    public void onFailed(Throwable t) {
                        Timber.d("onRunFail-Setting | %s ", t.toString());
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
//        new SettingLocal().delete(setting);
//        EventBus.getDefault().post(new SettingDeletedEvent(setting));
//        Timber.d("onCancel-Setting %s", throwable.toString());
    }


    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        long initialBackOff = 0;

        Timber.d("OnReRun-Setting %s", setting.toString());
        Timber.d("OnReRun-Setting Run Count %s", runCount);
        Timber.d("OnReRun-Setting Run Max Count %s", maxRunCount);

        if (shouldRetry(throwable)) {
            //TODO :: waktu yang gradual
            initialBackOff = runCount * 1000;
            RetryConstraint constraint = RetryConstraint
                    .createExponentialBackoff(runCount, initialBackOff); //1 detik sajah
            constraint.setApplyNewDelayToGroup(true);

            return constraint;
        }
        return RetryConstraint.RETRY;
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
