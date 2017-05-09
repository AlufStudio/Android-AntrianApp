
package com.abdymalikmulky.settingqueue.app.data.pond.response;

import com.abdymalikmulky.settingqueue.app.data.pond.Pond;
import com.abdymalikmulky.settingqueue.app.data.user.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PondResponse {

    @SerializedName("ponds")
    @Expose
    private List<Pond> ponds = null;
    @SerializedName("users")
    @Expose
    private List<User> users = null;

    public List<Pond> getPonds() {
        return ponds;
    }

    public void setPonds(List<Pond> ponds) {
        this.ponds = ponds;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "PondResponse{" +
                "ponds=" + ponds +
                ", users=" + users +
                '}';
    }
}
