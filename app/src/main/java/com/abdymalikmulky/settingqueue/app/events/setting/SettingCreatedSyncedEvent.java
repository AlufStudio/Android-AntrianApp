package com.abdymalikmulky.settingqueue.app.events.setting;

import com.abdymalikmulky.settingqueue.app.data.setting.Setting;

/**
 * Created by efishery on 04/05/17.
 */

public class SettingCreatedSyncedEvent {
    private Setting setting;

    public SettingCreatedSyncedEvent(Setting setting) {
        this.setting = setting;
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }
}
