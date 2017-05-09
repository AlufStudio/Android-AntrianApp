package com.abdymalikmulky.settingqueue.app.data.setting;

import java.util.List;

/**
 * Created by efishery on 09/05/17.
 */

public interface SettingDataSource {

    interface LoadSettingCallback {
        void onLoaded(List<Setting> settings);
        void onNoData(String msg);
    }
    interface SaveSettingCallback {
        void onSaved(Setting setting);
        void onFailed(Throwable t) throws Throwable;
    }
    interface SaveRemoteSettingCallback {
        void onSaved(Setting pond);
        void onFailed(Throwable t) throws Throwable;
    }


    void load(int pondId, LoadSettingCallback callback);

    void save(Setting setting, SaveSettingCallback callback) throws Throwable;

    void save(Setting setting, SaveRemoteSettingCallback callback) throws Throwable;



}