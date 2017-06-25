package com.jae.radioapp;

import com.crashlytics.android.Crashlytics;
import com.jae.radioapp.injection.component.AppComponent;
import com.jae.radioapp.injection.component.DaggerAppComponent;
import com.jae.radioapp.injection.module.AppModule;
import com.mhealth.core.BaseApplication;

import io.fabric.sdk.android.Fabric;

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
        // fabric
        Fabric.with(this, new Crashlytics());
        // dagger
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public static RadioApplication getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }


}
