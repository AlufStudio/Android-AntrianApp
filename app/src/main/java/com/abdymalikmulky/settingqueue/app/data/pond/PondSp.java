package com.abdymalikmulky.settingqueue.app.data.pond;

import android.content.Context;

import com.abdymalikmulky.settingqueue.helper.SharedPrefHelper;

/**
 * Created by efishery on 09/05/17.
 */

public class PondSp extends SharedPrefHelper {

    public static final String KEY_REMOTE_POND_ID = "KEY_REMOTE_POND_ID";
    public static final long DEF_REMOTE_POND_ID = 1;

    public PondSp(Context _context) {
        super(_context);
    }

    public long getLastPondId() {
        long id = getPref().getLong(KEY_REMOTE_POND_ID, DEF_REMOTE_POND_ID);
        getPref().edit().putLong(KEY_REMOTE_POND_ID, id + 1).commit();
        return id;
    }

    public void setLastPondId(long id) {
        getPref().edit().putLong(KEY_REMOTE_POND_ID, id).commit();
    }


}
