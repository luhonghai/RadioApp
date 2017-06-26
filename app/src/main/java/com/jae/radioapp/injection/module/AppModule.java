package com.jae.radioapp.injection.module;

import android.app.Application;
import android.content.Context;

import com.jae.radioapp.data.local.PreferenceHelper;
import com.jae.radioapp.data.remote.RadikoService;
import com.jae.radioapp.data.remote.ServiceUtils;
import com.jae.radioapp.data.remote.RadioService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alex on 4/5/17.
 */

@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    Context providesContext() {
        return mApplication;
    }

    @Singleton
    @Provides
    PreferenceHelper providesPreferenceHelper(Context context) {
        return new PreferenceHelper(context);
    }

    @Singleton
    @Provides
    RadioService providesDriverService(Context context) {
        return ServiceUtils.createService(RadioService.class, RadioService.END_POINT);
    }

    @Singleton
    @Provides
    RadikoService providesRadikoService(Context context) {
        return ServiceUtils.createServiceXML(RadikoService.class, RadikoService.END_POINT);
    }

}
