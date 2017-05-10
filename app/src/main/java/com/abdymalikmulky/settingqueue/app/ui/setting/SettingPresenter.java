package com.abdymalikmulky.settingqueue.app.ui.setting;


import com.abdymalikmulky.settingqueue.app.data.setting.Setting;
import com.abdymalikmulky.settingqueue.app.data.setting.SettingDataSource;
import com.abdymalikmulky.settingqueue.app.data.setting.SettingLocal;
import com.abdymalikmulky.settingqueue.app.data.setting.SettingRemote;
import com.abdymalikmulky.settingqueue.app.event.pond.DeletedPondEvent;
import com.abdymalikmulky.settingqueue.app.event.setting.CreatingSettingEvent;
import com.abdymalikmulky.settingqueue.app.job.CreateSettingJob;
import com.birbit.android.jobqueue.JobManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import timber.log.Timber;

//lagi lagi dibutuhkannya dagger
public class SettingPresenter implements SettingContract.Presenter {

    SettingContract.View mSettingView;

    JobManager jobManager;

    SettingLocal settingLocal;
    SettingRemote settingRemote;

    public SettingPresenter(SettingContract.View settingView, JobManager jobManager) {
        this.jobManager = jobManager;

        settingLocal = new SettingLocal();
        settingRemote = new SettingRemote();

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
        settingRemote.load(pondId, new SettingDataSource.LoadSettingCallback() {
            @Override
            public void onLoaded(List<Setting> settings) {
                mSettingView.showSetting(settings);
            }

            @Override
            public void onNoData(String msg) {
                mSettingView.showNoSetting(msg);
            }
        });
    }

    @Override
    public void saveSetting(long pondId, Setting setting) {
        setting.setPondId(pondId);
        jobManager.addJobInBackground(new CreateSettingJob(setting));

        /*setting.setPondId(pondId);
        settingRemote.save(setting, new SettingDataSource.SaveRemoteSettingCallback() {
            @Override
            public void onSaved(Setting setting) {
                mSettingView.showNewSetting(setting);
            }

            @Override
            public void onFailed(Throwable t) throws Throwable {
                mSettingView.showNoSetting(t.toString());
            }
        });*/
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CreatingSettingEvent settingEvent) {
        Timber.d("EventRun %s",settingEvent.getSetting().toString());
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DeletedPondEvent pondEvent) {

    }

}