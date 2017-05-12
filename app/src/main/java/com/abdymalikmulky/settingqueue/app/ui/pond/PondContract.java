package com.abdymalikmulky.settingqueue.app.ui.pond;

import com.abdymalikmulky.settingqueue.app.BasePresenter;
import com.abdymalikmulky.settingqueue.app.BaseView;
import com.abdymalikmulky.settingqueue.app.data.pond.Pond;

import java.util.List;

public interface PondContract {

    interface View extends BaseView<Presenter> {

        void showPonds(List<Pond> ponds);

        void showNoPond();

        void showDeletedPond(Pond pond);

        void showNewPondAdded(Pond pond);
    }
    interface Presenter extends BasePresenter {
        void loadPonds();

        void syncPonds();

        void savePond(Pond pond);
    }
}