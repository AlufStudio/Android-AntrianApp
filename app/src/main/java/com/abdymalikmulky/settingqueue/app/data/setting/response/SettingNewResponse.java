package com.abdymalikmulky.settingqueue.app.data.setting.response;

import com.abdymalikmulky.settingqueue.app.data.pond.Pond;
import com.abdymalikmulky.settingqueue.app.data.user.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by efishery on 04/05/17.
 */

public class SettingNewResponse {
    @SerializedName("user")
    @Expose
    private User user = null;

    @SerializedName("pond")
    @Expose
    private Pond pond = null;

    @SerializedName("setting")
    @Expose
    private User setting = null;


    public User getUser() {
        return user;
    }

    public Pond getPond() {
        return pond;
    }

    public User getSetting() {
        return setting;
    }

    @Override
    public String toString() {
        return "SettingNewResponse{" +
                "user=" + user +
                ", pond=" + pond +
                ", setting=" + setting +
                '}';
    }
}
