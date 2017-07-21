package com.kondenko.yamblzweather.dagger.modules;

import android.content.Context;

import com.kondenko.yamblzweather.App;
import com.kondenko.yamblzweather.ui.weather.dagger.WeatherSubcomponent;
import com.kondenko.yamblzweather.utils.SettingsManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = {WeatherSubcomponent.class})
public class AppModule {

    private App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public App provideApp() { return application; }

    @Provides
    @Singleton
    public Context provideContext() {
        return application.getBaseContext();
    }

    @Provides
    @Singleton
    public SettingsManager provideSettingsManager() {
        return new SettingsManager(application);
    }

}
