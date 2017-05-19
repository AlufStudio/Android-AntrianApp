package com.abdymalikmulky.settingqueue.app.ui.setting;


import com.abdymalikmulky.feederconnector.FeederApiClient;
import com.abdymalikmulky.settingqueue.app.data.setting.Setting;
import com.abdymalikmulky.settingqueue.app.data.setting.SettingDataSource;
import com.abdymalikmulky.settingqueue.app.data.setting.SettingFeeder;
import com.abdymalikmulky.settingqueue.app.data.setting.SettingLocal;
import com.abdymalikmulky.settingqueue.app.data.setting.SettingRemote;
import com.abdymalikmulky.settingqueue.app.events.setting.SettingCreatedSyncedEvent;
import com.abdymalikmulky.settingqueue.app.events.setting.SettingCreatedSyncingEvent;
import com.abdymalikmulky.settingqueue.app.events.setting.SettingDeletedEvent;
import com.abdymalikmulky.settingqueue.app.events.setting.SettingFetchedEvent;
import com.abdymalikmulky.settingqueue.app.events.setting.SettingFetchingEvent;
import com.abdymalikmulky.settingqueue.app.jobs.CreateSettingJob;
import com.abdymalikmulky.settingqueue.app.jobs.FetchSettingByPondJob;
import com.birbit.android.jobqueue.JobManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import timber.log.Timber;

//lagi lagi dibutuhkannya dagger
public class SettingPresenter implements SettingContract.Presenter {

    private SettingContract.View mSettingView;

    private JobManager jobManager;


    private SettingLocal settingLocal;
    private SettingRemote settingRemote;
    private SettingFeeder settingFeeder;

    public SettingPresenter(SettingContract.View settingView, JobManager jobManager, FeederApiClient feederApiClient) {
        this.jobManager = jobManager;

        settingLocal = new SettingLocal();
        settingRemote = new SettingRemote();
        //repo feeder
        settingFeeder = new SettingFeeder(feederApiClient);

        mSettingView = settingView;
        mSettingView.setPresenter(this);
    }

    @Override
    public void start() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void stop() {
        try {
            EventBus.getDefault().unregister(this);
        } catch (Throwable t){
            //this may crash if registration did not go through. just be safe
        }
    }

    @Override
    public void loadSetting(long pondId) {
        settingLocal.load(pondId, new SettingDataSource.LoadSettingCallback() {
            @Override
            public void onLoaded(List<Setting> settings) {
                mSettingView.showSettings(settings);
            }

            @Override
            public void onNoData(String msg) {
                mSettingView.showNoSetting(msg);
            }

            @Override
            public void onFailed(Throwable t) throws Throwable {
            }
        });
    }

    @Override
    public void loadSettingNotSynced(final long pondId) {
        settingLocal.getNotSynced(pondId, new SettingDataSource.LoadSettingCallback() {
            @Override
            public void onLoaded(List<Setting> settings) {
                for (Setting setting : settings) {
                    try {
                        saveSetting(pondId, setting);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            }

            @Override
            public void onNoData(String msg) {

            }

            @Override
            public void onFailed(Throwable t) throws Throwable {

            }
        });
    }

    @Override
    public void loadSettingRemote(long pondId) {
        loadSettingNotSynced(pondId);
        jobManager.addJobInBackground(new FetchSettingByPondJob(pondId));
    }

    @Override
    public void loadSettingFeeder(final long pondId) {
        settingFeeder.getLast(pondId, new SettingDataSource.GetLastSettingCallback() {
            @Override
            public void onGet(Setting setting) {
                Timber.d("SettingFeeder %s", setting.toString());
                //get last setting untuk keperluan kelengkapan data pada dashboard, sebagai pelengkap data yang tidak ada di feeder
                settingLocal.getLast(pondId, new SettingDataSource.GetLastSettingCallback() {
                    @Override
                    public void onGet(Setting setting) {
                        mSettingView.showSettingFeeder(setting);
                        Timber.d("SettingLocal %s", setting.toString());
                    }
                    @Override
                    public void onNoData(String msg) {
                        mSettingView.showNoSetting(msg);
                    }
                    @Override
                    public void onFailed(String msg) {
                        mSettingView.showFailSetting(666, msg);
                    }
                });
            }

            @Override
            public void onNoData(String msg) {
                mSettingView.showNoSetting(msg);

            }

            @Override
            public void onFailed(String msg) {
                mSettingView.showFailSetting(666, msg);

            }
        });
    }

    @Override
    public void saveSettingWithFeeder(long pondId, Setting setting) throws Throwable {
        setting.setPondId(pondId);
        settingFeeder.save(setting, new SettingDataSource.SaveSettingCallback() {
            @Override
            public void onSaved(Setting setting) {
                Timber.d("FeederApiClient %s", setting.toString());
                jobManager.addJobInBackground(new CreateSettingJob(setting));
            }
            @Override
            public void onFailed(Throwable t) {
                mSettingView.showFailSetting(666, t.toString());
            }
        });
    }

    @Override
    public void saveSetting(long pondId, Setting setting) throws Throwable {
        setting.setPondId(pondId);
        jobManager.addJobInBackground(new CreateSettingJob(setting));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SettingCreatedSyncingEvent settingEvent) {
        loadSetting(settingEvent.getSetting().getPondId());
        showLogType("Save on local , data : " + settingEvent.getSetting().getFeedWeight());
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SettingCreatedSyncedEvent settingEvent) {
        loadSetting(settingEvent.getSetting().getPondId());
        showLogType("Save on remote, data : " + settingEvent.getSetting().getFeedWeight());
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SettingDeletedEvent settingEvent) {
        loadSetting(settingEvent.getSetting().getPondId());
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SettingFetchedEvent settingEvent) {
        loadSetting(settingEvent.getPondId());
        showLogType("Fetch Remote, data : " + settingEvent.getPondId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SettingFetchingEvent settingEvent) {
        loadSetting(settingEvent.getPondId());
        showLogType("Fetch local, data : " + settingEvent.getPondId());
    }


    //For Logging Lebih Kontekstual
    private void showLogType(String type) {
        mSettingView.showLogMessageType(type);
    }
}