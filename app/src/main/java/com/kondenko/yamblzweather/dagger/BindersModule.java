package com.kondenko.yamblzweather.dagger;

import android.app.Activity;

import com.kondenko.yamblzweather.ui.weather.WeatherActivity;
import com.kondenko.yamblzweather.ui.weather.dagger.WeatherSubcomponent;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module
public abstract class BindersModule {

    @Binds
    @IntoMap
    @ActivityKey(WeatherActivity.class)
    public abstract AndroidInjector.Factory<? extends Activity>
    bindMainActivityInjectorFactory(WeatherSubcomponent.Builder builder);

}
