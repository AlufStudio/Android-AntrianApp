package com.abdymalikmulky.settingqueue.app.ui.setting;


import com.abdymalikmulky.settingqueue.app.data.pond.Pond;
import com.abdymalikmulky.settingqueue.app.data.pond.PondLocal;
import com.abdymalikmulky.settingqueue.app.data.pond.PondRemote;
import com.abdymalikmulky.settingqueue.app.data.pond.PondRepo;
import com.birbit.android.jobqueue.JobManager;

import org.greenrobot.eventbus.EventBus;

//lagi lagi dibutuhkannya dagger
public class SettingPresenter implements SettingContract.Presenter {

    SettingContract.View mPondView;

    //REPO
    PondRepo pondRepo;
    PondLocal pondLocal;
    PondRemote pondRemote;

    JobManager jobManager;

    public SettingPresenter(SettingContract.View pondView, JobManager jobManager) {
        this.jobManager = jobManager;

        pondLocal = new PondLocal();
        pondRemote = new PondRemote();
        pondRepo = new PondRepo(pondRemote, pondLocal);


        mPondView = pondView;
        mPondView.setPresenter(this);


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
    public void loadPonds() {

    }

    @Override
    public void saveSetting(Pond pond) {

    }
}