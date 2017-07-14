package com.kondenko.yamblzweather.ui.weather.dagger;

import com.kondenko.yamblzweather.dagger.modules.NetModule;
import com.kondenko.yamblzweather.ui.about.FragmentAbout;
import com.kondenko.yamblzweather.ui.weather.FragmentWeather;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {WeatherModule.class})
//@Subcomponent
public interface WeatherSubcomponent extends AndroidInjector<FragmentWeather> {

    @Subcomponent.Builder
    public abstract class Builder extends AndroidInjector.Builder<FragmentWeather> {}

}
