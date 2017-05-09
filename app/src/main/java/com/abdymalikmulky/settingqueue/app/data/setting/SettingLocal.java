package com.abdymalikmulky.settingqueue.app.data.setting;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * Created by efishery on 09/05/17.
 */

public class SettingLocal implements SettingDataSource {

    public SettingLocal() {
    }

    @Override
    public void load(int pondId, LoadSettingCallback callback) {
        List<Setting> settings = SQLite.select()
                .from(Setting.class)
                .where(Setting_Table.pondId.eq(pondId))
                .orderBy(Setting_Table.createdAt,true)
                .queryList();
        if(settings.size() > 0){
            callback.onLoaded(settings);
        }else {
            callback.onNoData("No Data");
        }
    }

    @Override
    public void save(Setting newSetting, SaveSettingCallback callback) throws Throwable {
        long settingRemoteId = 0;
        Setting setting = new Setting();
        setting = newSetting;
        settingRemoteId = setting.insert();

        if(settingRemoteId > 0){
            setting.setId((int) settingRemoteId);
            callback.onSaved(setting);
        }else{
            callback.onFailed(new Throwable());
        }
    }

    @Override
    public void save(Setting setting, SaveRemoteSettingCallback callback) throws Throwable {
    }
}
