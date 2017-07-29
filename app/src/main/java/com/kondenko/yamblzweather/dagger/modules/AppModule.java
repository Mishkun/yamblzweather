package com.kondenko.yamblzweather.dagger.modules;

import android.content.Context;

import com.kondenko.yamblzweather.App;
import com.kondenko.yamblzweather.ui.weather.dagger.WeatherSubcomponent;
import com.kondenko.yamblzweather.utils.SettingsManager;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.kondenko.yamblzweather.Const.JOB;
import static com.kondenko.yamblzweather.Const.UI;

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

    @Provides
    @Singleton
    @Named(JOB)
    public Scheduler provideBackgroundScheduler(){
        return Schedulers.io();
    }

    @Provides
    @Singleton
    @Named(UI)
    public Scheduler provideUiScheduler(){
        return AndroidSchedulers.mainThread();
    }
}
