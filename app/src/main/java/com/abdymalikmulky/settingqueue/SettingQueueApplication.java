package com.abdymalikmulky.settingqueue;

import android.app.Application;
import android.content.res.Configuration;

import com.abdymalikmulky.settingqueue.network.NetworkModule;
import com.raizlabs.android.dbflow.config.FlowManager;

import timber.log.Timber;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 5/2/17.
 */

public class SettingQueueApplication extends Application{

    private AppComponent appComponent;

    private static SettingQueueApplication instance;

    public static SettingQueueApplication get() { return instance; }


    @Override
    public void onCreate() {
        super.onCreate();
        //instance
        instance = this;

        FlowManager.init(this);
        Timber.plant(new Timber.DebugTree());
        appComponent = initDagger(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
    protected  AppComponent initDagger(SettingQueueApplication application){
        return DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .networkModule(new NetworkModule())
                .build();
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
