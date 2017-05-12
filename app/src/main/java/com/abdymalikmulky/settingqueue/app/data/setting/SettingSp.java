package com.abdymalikmulky.settingqueue.app.data.setting;

import android.content.Context;

import com.abdymalikmulky.settingqueue.helper.SharedPrefHelper;

/**
 * Created by efishery on 09/05/17.
 */

public class SettingSp extends SharedPrefHelper {

    public static final String KEY_REMOTE_SETTING_ID = "KEY_REMOTE_SETTING_ID";
    public static final long DEF_REMOTE_SETTING_ID = 1;

    public SettingSp(Context _context) {
        super(_context);
    }

    public long getLastSettingId() {
        long id = getPref().getLong(KEY_REMOTE_SETTING_ID, DEF_REMOTE_SETTING_ID);
        getPref().edit().putLong(KEY_REMOTE_SETTING_ID, id + 1).commit();
        return id;
    }

    public void setLastSettingId(long id) {
        getPref().edit().putLong(KEY_REMOTE_SETTING_ID, id).commit();
    }


}
