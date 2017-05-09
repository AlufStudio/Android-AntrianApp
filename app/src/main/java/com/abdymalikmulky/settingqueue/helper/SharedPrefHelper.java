package com.abdymalikmulky.settingqueue.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by efishery on 23/02/17.
 */

public class SharedPrefHelper {

    private Context _context;
    public SharedPreferences pref;
    public SharedPreferences.Editor editor;
    public static final String PREF_NAME = "pref_efishery";
    int PRIVATE_MODE = 0;

    public SharedPrefHelper(Context _context) {
        this._context = _context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public SharedPreferences getPref() {
        return pref;
    }

    public void clearAllPreference() {
        editor.clear();
        editor.apply();
    }
}
