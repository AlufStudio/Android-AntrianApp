package com.abdymalikmulky.settingqueue.app.events.pond;

import com.abdymalikmulky.settingqueue.app.data.pond.Pond;

/**
 * Created by efishery on 04/05/17.
 */

public class PondCreatedSyncedEvent {
    private Pond pond;

    public PondCreatedSyncedEvent(Pond pond) {
        this.pond = pond;
    }

    public Pond getPond() {
        return pond;
    }

    public void setPond(Pond pond) {
        this.pond = pond;
    }
}
