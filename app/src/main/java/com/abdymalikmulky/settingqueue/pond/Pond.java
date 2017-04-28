package com.abdymalikmulky.settingqueue.pond;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 4/28/17.
 */

public class Pond {
    int id;
    int uuid;
    String name;
    String user;

    public Pond() {
    }

    public Pond(int id, int uuid, String name, String user) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
