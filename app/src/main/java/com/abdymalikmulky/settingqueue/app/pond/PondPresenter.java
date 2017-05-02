package com.abdymalikmulky.settingqueue.app.pond;


import com.abdymalikmulky.settingqueue.app.data.pond.Pond;
import com.abdymalikmulky.settingqueue.app.data.pond.PondDataSource;
import com.abdymalikmulky.settingqueue.app.data.pond.PondLocal;

import java.util.List;

public class PondPresenter implements PondContract.Presenter {

    PondContract.View mPondView;
    PondLocal pondLocal;

    public PondPresenter(PondContract.View pondView) {
        pondLocal = new PondLocal();
        mPondView = pondView;
        mPondView.setPresenter(this);
    }

    @Override
    public void start() {
        loadPonds();
    }

    @Override
    public void loadPonds() {
        pondLocal.load(new PondDataSource.LoadPondCallback() {
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
        pondLocal.save(pond, new PondDataSource.SavePondCallback() {
            @Override
            public void onSaved(Pond pond) {
                mPondView.showNewPondAdded(pond);
            }

            @Override
            public void onFailed(String msg) {
                mPondView.showNoPond();
            }
        });
    }
}