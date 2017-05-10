package com.abdymalikmulky.settingqueue.app.ui.pond;


import com.abdymalikmulky.settingqueue.app.data.pond.Pond;
import com.abdymalikmulky.settingqueue.app.data.pond.PondDataSource;
import com.abdymalikmulky.settingqueue.app.data.pond.PondLocal;
import com.abdymalikmulky.settingqueue.app.data.pond.PondRemote;
import com.abdymalikmulky.settingqueue.app.data.pond.PondRepo;
import com.abdymalikmulky.settingqueue.app.event.pond.CreatingPondEvent;
import com.abdymalikmulky.settingqueue.app.event.pond.DeletedPondEvent;
import com.abdymalikmulky.settingqueue.app.job.CreatePondJob;
import com.birbit.android.jobqueue.JobManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import timber.log.Timber;

//lagi lagi dibutuhkannya dagger
public class PondPresenter implements PondContract.Presenter {

    PondContract.View mPondView;

    //REPO
    PondRepo pondRepo;
    PondLocal pondLocal;
    PondRemote pondRemote;

    JobManager jobManager;

    public PondPresenter(PondContract.View pondView, JobManager jobManager) {
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
        pondRepo.load(new PondDataSource.LoadPondCallback() {
            @Override
            public void onLoaded(List<Pond> ponds) {
                mPondView.showPonds(ponds);
            }

            @Override
            public void onNoData(String msg) {
                mPondView.showNoPond();
            }
        });
    }

    @Override
    public void savePond(Pond pond) {
        jobManager.addJobInBackground(new CreatePondJob(pond));
        /*pondRepo.save(pond, new PondDataSource.SavePondCallback() {
            @Override
            public void onSaved(Pond pond) {
                mPondView.showNewPondAdded(pond);
            }

            @Override
            public void onFailed(String msg) {
                mPondView.showNoPond();
            }
        });*/
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CreatingPondEvent pondEvent) {
        Timber.d("EventRun %s",pondEvent.getPond().toString());
        loadPonds();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DeletedPondEvent pondEvent) {
        mPondView.showDeletedPond(pondEvent.getPond());
        loadPonds();
    }

}