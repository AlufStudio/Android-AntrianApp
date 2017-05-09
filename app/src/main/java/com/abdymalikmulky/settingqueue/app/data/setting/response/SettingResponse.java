
package com.abdymalikmulky.settingqueue.app.data.setting.response;

import com.abdymalikmulky.settingqueue.app.data.pond.Pond;
import com.abdymalikmulky.settingqueue.app.data.setting.Setting;
import com.abdymalikmulky.settingqueue.app.data.user.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SettingResponse {


    @SerializedName("settings")
    @Expose
    private List<Setting> settings = null;
    @SerializedName("pond")
    @Expose
    private Pond pond = null;
    @SerializedName("user")
    @Expose
    private User user = null;

    public List<Setting> getSettings() {
        return settings;
    }

    public Pond getPond() {
        return pond;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "SettingResponse{" +
                "settings=" + settings +
                ", pond=" + pond +
                ", user=" + user +
                '}';
    }
}
