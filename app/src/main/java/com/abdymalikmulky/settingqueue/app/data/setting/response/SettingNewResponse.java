package com.abdymalikmulky.settingqueue.app.data.setting.response;

import com.abdymalikmulky.settingqueue.app.data.pond.Pond;
import com.abdymalikmulky.settingqueue.app.data.setting.Setting;
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
    private Setting setting = null;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Pond getPond() {
        return pond;
    }

    public void setPond(Pond pond) {
        this.pond = pond;
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
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
