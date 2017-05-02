package com.abdymalikmulky.settingqueue;

import com.abdymalikmulky.settingqueue.app.pond.PondActivity;
import com.abdymalikmulky.settingqueue.network.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 5/2/17.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    void inject(PondActivity activity);
}
