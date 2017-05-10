package com.abdymalikmulky.settingqueue.app.ui.setting;


import com.abdymalikmulky.settingqueue.app.data.pond.Pond;
import com.birbit.android.jobqueue.JobManager;

import org.greenrobot.eventbus.EventBus;

//lagi lagi dibutuhkannya dagger
public class SettingPresenter implements SettingContract.Presenter {

    SettingContract.View mSettingView;

    JobManager jobManager;

    public SettingPresenter(SettingContract.View settingView, JobManager jobManager) {
        this.jobManager = jobManager;

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
    public void loadSetting() {

    }

    @Override
    public void saveSetting(Pond pond) {

    }
}