package com.kondenko.yamblzweather.dagger.modules;

import com.kondenko.yamblzweather.App;
import com.kondenko.yamblzweather.ui.weather.dagger.WeatherSubcomponent;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = {WeatherSubcomponent.class})
public class AppModule {

    public App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public App provideApp() { return application; }

}
