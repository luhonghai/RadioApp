package com.jae.radioapp;

import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.jae.radioapp.injection.component.AppComponent;
import com.jae.radioapp.injection.component.DaggerAppComponent;
import com.jae.radioapp.injection.module.AppModule;
import com.mhealth.core.BaseApplication;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by alex on 6/7/17.
 */

public class RadioApplication extends BaseApplication {

    private static RadioApplication instance;
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // enable multi dex
        MultiDex.install(this);

        // fabric
        Fabric.with(this, new Crashlytics());

        // dagger
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();

        Stetho.initializeWithDefaults(this); // network interceptor

        Timber.plant(new Timber.DebugTree()); // logging
    }

    public static RadioApplication getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }


}
