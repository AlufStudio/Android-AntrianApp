package com.abdymalikmulky.settingqueue.app.event;

import com.abdymalikmulky.settingqueue.app.data.pond.Pond;

/**
 * Created by efishery on 04/05/17.
 */

public class CreatingPondEvent {
    private Pond pond;

    public CreatingPondEvent(Pond pond) {
        this.pond = pond;
    }

    public Pond getPond() {
        return pond;
    }

    public void setPond(Pond pond) {
        this.pond = pond;
    }
}
