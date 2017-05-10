package com.abdymalikmulky.settingqueue.app.data.setting;

import com.abdymalikmulky.settingqueue.app.data.pond.Pond;
import com.abdymalikmulky.settingqueue.app.data.pond.PondDataSource;
import com.abdymalikmulky.settingqueue.app.data.pond.Pond_Table;
import com.abdymalikmulky.settingqueue.util.AppUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * Created by efishery on 09/05/17.
 */

public class SettingLocal implements SettingDataSource {

    public SettingLocal() {
    }

    @Override
    public void load(long pondId, LoadSettingCallback callback) {
        List<Setting> settings = SQLite.select()
                .from(Setting.class)
                .where(Setting_Table.pondId.eq(pondId))
                .orderBy(Setting_Table.createdAt,false)
                .queryList();
        if(settings.size() > 0){
            callback.onLoaded(settings);
        }else {
            callback.onNoData("No Data");
        }
    }

    @Override
    public void save(Setting newSetting, SaveSettingCallback callback) {
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

    public void delete(Setting setting){
        SQLite.delete(Setting.class)
                .where(Setting_Table.clientId.is(setting.getClientId()))
                .async()
                .execute();
    }

    public void update(Pond pondUpdate, PondDataSource.SavePondCallback callback){
        SQLite.update(Pond.class)
                .set(Pond_Table.syncState.eq(AppUtils.STATE_SYNCED),
                        Pond_Table.id.eq(pondUpdate.getId()),
                        Pond_Table.createdAt.eq(pondUpdate.getCreatedAt()),
                        Pond_Table.updatedAt.eq(pondUpdate.getUpdatedAt()))
                .where(Pond_Table.clientId.is(pondUpdate.getClientId()))
                .async()
                .execute();
        callback.onSaved(pondUpdate);
    }

}
