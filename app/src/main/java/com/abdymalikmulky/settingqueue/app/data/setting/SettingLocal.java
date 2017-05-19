package com.abdymalikmulky.settingqueue.app.data.setting;

import com.abdymalikmulky.settingqueue.util.AppUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Where;

import java.util.List;

import timber.log.Timber;

import static com.raizlabs.android.dbflow.sql.language.Method.count;

/**
 * Created by efishery on 09/05/17.
 */

public class SettingLocal implements SettingDataSource {

    public SettingLocal() {
    }

    public Setting get(long id){
        return SQLite.select().from(Setting.class)
                .where(Setting_Table.id.eq(id)).querySingle();
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
    public void getLast(long pondId, GetLastSettingCallback callback) {
       Where<Setting> queryWhere = SQLite.select()
                .from(Setting.class)
                .where(Setting_Table.pondId.eq(pondId));
        Setting setting = queryWhere.querySingle();

        if(queryWhere.count() > 0){
            callback.onGet(setting);
        }else {
            callback.onNoData("No Data");
        }
    }

    @Override
    public void save(Setting newSetting, SaveSettingCallback callback) {
        if (!isExist(newSetting.getClientId())) {
            long settingRemoteId = 0;
            Setting setting = newSetting;
            settingRemoteId = setting.insert();

            if (settingRemoteId > 0) {
                setting.setId((int) settingRemoteId);
                callback.onSaved(setting);
            } else {
                callback.onFailed(new Throwable());
            }
        }

    }

    @Override
    public void save(Setting setting, SaveRemoteSettingCallback callback) throws Throwable {
    }

    public void delete(Setting setting) {
        SQLite.delete(Setting.class)
                .where(Setting_Table.clientId.is(setting.getClientId()))
                .async()
                .execute();
    }

    public void updateAll(Setting settingUpdate, SaveSettingCallback callback){
        SQLite.update(Setting.class)
                .set(Setting_Table.feedWeight.eq(settingUpdate.feedWeight),
                        Setting_Table.fishWeight.eq(settingUpdate.fishWeight),
                        Setting_Table.freq.eq(settingUpdate.freq))
                .where(Setting_Table.clientId.is(settingUpdate.getClientId()))
                .async()
                .execute();
        callback.onSaved(settingUpdate);
    }

    public boolean isExist(String clientId){
        int size = (int) SQLite.select(count(Setting_Table.clientId)).from(Setting.class)
                .where(Setting_Table.clientId.eq(clientId)).count();
        return size > 0;
    }

    //TODO :: deleted data atau flagging ?? buat di feeder in pond
    public void saveOrUpdate(List<Setting> settings){
        for (Setting setting : settings) {
            if(isExist(setting.getClientId())){
                updateAll(setting, new SaveSettingCallback() {
                    @Override
                    public void onSaved(Setting setting) {
                        Timber.d("SaveOrUpdate %s %s","Update",setting.toString());
                    }
                    @Override
                    public void onFailed(Throwable t) {
                        Timber.d("SaveOrUpdate %s %s","Update-Fail",t.toString());
                    }
                });
            }else{
                setting.setSyncState(AppUtils.STATE_SYNCED);
                save(setting, new SaveSettingCallback() {
                    @Override
                    public void onSaved(Setting setting) {
                        Timber.d("SaveOrUpdate %s %s","SAVE",setting.toString());
                    }

                    @Override
                    public void onFailed(Throwable t) {
                        Timber.d("SaveOrUpdate %s %s","SAVE-fail",t.toString());
                    }
                });
            }
        }
    }

    public void getNotSynced(long pondId, LoadSettingCallback callback) {
        List<Setting> settings = SQLite.select()
                .from(Setting.class)
                .where(Setting_Table.pondId.eq(pondId))
                .and(Setting_Table.syncState.eq(AppUtils.STATE_NOT_SYNCED))
                .orderBy(Setting_Table.createdAt, false)
                .queryList();
        if (settings.size() > 0) {
            callback.onLoaded(settings);
        } else {
            callback.onNoData("No Data");
        }
    }

    public void updateSync(Setting settingUpdate, SaveSettingCallback callback){
        SQLite.update(Setting.class)
                .set(Setting_Table.id.eq(settingUpdate.getId()),
                        Setting_Table.syncState.eq(AppUtils.STATE_SYNCED),
                        Setting_Table.createdAt.eq(settingUpdate.getCreatedAt()))
                .where(Setting_Table.clientId.is(settingUpdate.getClientId()))
                .async()
                .execute();
        callback.onSaved(settingUpdate);
    }

}
