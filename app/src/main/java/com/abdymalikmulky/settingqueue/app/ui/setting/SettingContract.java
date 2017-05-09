package com.abdymalikmulky.settingqueue.app.ui.setting;

import com.abdymalikmulky.settingqueue.app.BasePresenter;
import com.abdymalikmulky.settingqueue.app.BaseView;
import com.abdymalikmulky.settingqueue.app.data.pond.Pond;

public interface SettingContract {

    interface View extends BaseView<Presenter> {
        void showSetting(long pondId);

        void showNoSetting();

        void showNewSetting();
    }
    interface Presenter extends BasePresenter {
        void loadPonds();

        void saveSetting(Pond pond);
    }
}