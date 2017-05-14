package com.abdymalikmulky.settingqueue.app.events.setting;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 5/12/17.
 */

public class FetchedSettingEvent {
    long pondId;

    public FetchedSettingEvent(long pondId) {
        this.pondId = pondId;
    }

    public long getPondId() {
        return pondId;
    }

    public void setPondId(long pondId) {
        this.pondId = pondId;
    }
}
