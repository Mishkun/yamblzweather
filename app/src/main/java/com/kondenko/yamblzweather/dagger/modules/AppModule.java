package com.kondenko.yamblzweather.dagger.modules;

import com.kondenko.yamblzweather.App;
import com.kondenko.yamblzweather.dagger.components.AppComponent;
import com.kondenko.yamblzweather.ui.main.dagger.MainSubcomponent;
import com.kondenko.yamblzweather.ui.weather.dagger.WeatherSubcomponent;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = {MainSubcomponent.class, WeatherSubcomponent.class})
public class AppModule {

    public App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public App provideApp() { return application; }

}
