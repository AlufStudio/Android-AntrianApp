package com.abdymalikmulky.settingqueue.app.ui.setting;

import com.abdymalikmulky.settingqueue.app.BasePresenter;
import com.abdymalikmulky.settingqueue.app.BaseView;
import com.abdymalikmulky.settingqueue.app.data.setting.Setting;

import java.util.List;

public interface SettingContract {

    interface View extends BaseView<Presenter> {
        void showSettings(List<Setting> settings);

        void showNoSetting(String msg);

        void showSettingFeeder(Setting setting);

        void showFailSetting(int code, String message);

        void showLogMessageType(String type);
    }
    interface Presenter extends BasePresenter {
        void loadSetting(long pondId);

        void loadSettingNotSynced(long pondId);

        void loadSettingRemote(long pondId);

        void loadSettingFeeder(long pondId);

        void saveSetting(long pondId, Setting setting) throws Throwable;

        void saveSettingWithFeeder(long pondId, Setting setting) throws Throwable;

    }
}