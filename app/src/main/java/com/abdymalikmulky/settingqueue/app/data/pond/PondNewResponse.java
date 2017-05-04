package com.abdymalikmulky.settingqueue.app.data.pond;

import com.abdymalikmulky.settingqueue.app.data.user.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by efishery on 04/05/17.
 */

public class PondNewResponse {
    @SerializedName("pond")
    @Expose
    private Pond pond = null;
    @SerializedName("user")
    @Expose
    private User user = null;


    public Pond getPond() {
        return pond;
    }

    public void setPond(Pond pond) {
        this.pond = pond;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PondNewResponse{" +
                "pond=" + pond +
                ", user=" + user +
                '}';
    }
}
