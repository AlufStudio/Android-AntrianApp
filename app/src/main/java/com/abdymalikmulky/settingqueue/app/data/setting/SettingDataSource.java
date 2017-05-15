package com.abdymalikmulky.settingqueue.app.data.setting;

import java.util.List;

/**
 * Created by efishery on 09/05/17.
 */

public interface SettingDataSource {

    interface LoadSettingCallback {
        void onLoaded(List<Setting> settings);
        void onNoData(String msg);
        void onFailed(Throwable t) throws Throwable;
    }
    interface GetLastSettingCallback {
        void onGet(Setting setting);
        void onNoData(String msg);
        void onFailed(String msg);
    }

    interface SaveSettingCallback {
        void onSaved(Setting setting);
        void onFailed(Throwable t);
    }
    interface SaveRemoteSettingCallback {
        void onSaved(Setting SETTING);
        void onFailed(Throwable t) throws Throwable;
    }


    void load(long pondId, LoadSettingCallback callback);

    void getLast(long pondId, GetLastSettingCallback callback);

    void save(Setting setting, SaveSettingCallback callback) throws Throwable;

    void save(Setting setting, SaveRemoteSettingCallback callback) throws Throwable;



}
