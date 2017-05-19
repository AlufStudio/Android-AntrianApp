package com.abdymalikmulky.settingqueue.app.jobs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.abdymalikmulky.settingqueue.app.data.setting.Setting;
import com.abdymalikmulky.settingqueue.app.data.setting.SettingDataSource;
import com.abdymalikmulky.settingqueue.app.data.setting.SettingLocal;
import com.abdymalikmulky.settingqueue.app.data.setting.SettingRemote;
import com.abdymalikmulky.settingqueue.app.events.setting.SettingFetchedEvent;
import com.abdymalikmulky.settingqueue.app.events.setting.SettingFetchingEvent;
import com.abdymalikmulky.settingqueue.app.jobs.util.Group;
import com.abdymalikmulky.settingqueue.app.jobs.util.NetworkException;
import com.abdymalikmulky.settingqueue.app.jobs.util.Priority;
import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import timber.log.Timber;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 5/3/17.
 */

//Dibutuhkannya dagger agar hidup menjadi menyenangkan
public class FetchSettingByPondJob extends Job{

    private FetchSettingByPondJob INSTANCE;

    private long pondId;

    public FetchSettingByPondJob(long pondId) {
        super(new Params(Priority.LOW).requireNetwork().persist().groupBy(Group.POST_JOB));
        this.pondId = pondId;
    }

    @Override
    public void onAdded() {
        Timber.d("onAdded-Fetch-Setting | %s",pondId);
        //load di local dulu untuk UI
        EventBus.getDefault().post(new SettingFetchingEvent(pondId));
    }
    @Override
    public void onRun() throws Throwable {
        //OnRun|onAdded|Kegagalan|DataFail|OnReRun|onCancel
        //TODO:: tambahin update pondId, ganti fielnya harusnya make pond client id
        Timber.d("onRun-Fetch-Setting | %s",pondId);
        new SettingRemote().load(pondId, new SettingDataSource.LoadSettingCallback() {
            @Override
            public void onLoaded(List<Setting> settings) {
                Timber.d("onRun-Fetch-Setting-Load | %s",pondId);

                new SettingLocal().saveOrUpdate(settings);

                EventBus.getDefault().post(new SettingFetchedEvent(pondId));
            }

            @Override
            public void onNoData(String msg) {
                Timber.d("onRun-Fetch-Setting-noData | %s",pondId);

                EventBus.getDefault().post(new SettingFetchedEvent(pondId));
            }

            @Override
            public void onFailed(Throwable t) throws Throwable {
                throw  t;
            }
        });
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        Timber.d("onCancel-Fetch-Setting | %s",pondId);
        Timber.d("onCancel-Fetch-Setting | %s",cancelReason);

    }


    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        long initialBackOff = 0;

        Timber.d("onReRun-Fetch-Setting | %s",pondId);

        return null;
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
