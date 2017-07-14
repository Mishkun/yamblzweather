package com.kondenko.yamblzweather.dagger;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.kondenko.yamblzweather.ui.main.MainActivity;
import com.kondenko.yamblzweather.ui.main.dagger.MainSubcomponent;
import com.kondenko.yamblzweather.ui.weather.FragmentWeather;
import com.kondenko.yamblzweather.ui.weather.dagger.WeatherSubcomponent;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module
public abstract class BindersModule {

    @Binds
    @IntoMap
    @FragmentKey(FragmentWeather.class)
    public abstract AndroidInjector.Factory<? extends Fragment>
    bindWeatherFragmentInjectorFactory(WeatherSubcomponent.Builder builder);

    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    public abstract AndroidInjector.Factory<? extends Activity>
    bindMainActivityInjectorFactory(MainSubcomponent.Builder builder);

}
