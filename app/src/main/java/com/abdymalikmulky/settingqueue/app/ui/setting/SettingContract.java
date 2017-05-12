package com.abdymalikmulky.settingqueue.app.ui.setting;

import com.abdymalikmulky.settingqueue.app.BasePresenter;
import com.abdymalikmulky.settingqueue.app.BaseView;
import com.abdymalikmulky.settingqueue.app.data.setting.Setting;

import java.util.List;

public interface SettingContract {

    interface View extends BaseView<Presenter> {
        void showSetting(List<Setting> settings);

        void showNoSetting(String msg);

        void showNewSetting(Setting setting);
    }
    interface Presenter extends BasePresenter {
        void loadSetting(long pondId);

        void syncSetting(long pondId);

        void saveSetting(long pondId, Setting setting) throws Throwable;
    }
}