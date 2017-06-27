package com.jae.radioapp.injection.component;

import com.jae.radioapp.injection.module.AppModule;
import com.jae.radioapp.ui.FragmentChannelListPresenter;
import com.jae.radioapp.ui.FragmentPlayerFullPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by alex on 4/5/17.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(FragmentChannelListPresenter presenter);

    void inject(FragmentPlayerFullPresenter presenter);

}
