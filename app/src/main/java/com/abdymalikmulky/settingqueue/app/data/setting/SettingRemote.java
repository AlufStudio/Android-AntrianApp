package com.abdymalikmulky.settingqueue.app.data.setting;

import com.abdymalikmulky.settingqueue.helper.ApiHelper;

/**
 * Created by efishery on 09/05/17.
 */

public class SettingRemote implements SettingDataSource {
    SettingApi api;

    public SettingRemote() {
        api = ApiHelper.client().create(SettingApi.class);
    }

    @Override
    public void load(int pondId, LoadSettingCallback callback) {
        
    }

    @Override
    public void save(Setting setting, SaveSettingCallback callback) {

    }

    @Override
    public void save(Setting setting, SaveRemoteSettingCallback callback) throws Throwable {

    }
}
