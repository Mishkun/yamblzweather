package com.kondenko.yamblzweather.ui.weather.dagger;

import com.kondenko.yamblzweather.model.WeatherService;
import com.kondenko.yamblzweather.ui.weather.FragmentWeather;
import com.kondenko.yamblzweather.ui.weather.WeatherPresenter;
import com.kondenko.yamblzweather.ui.weather.WeatherView;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public abstract class WeatherModule {

    @Binds
    public abstract WeatherView provideWeatherView(FragmentWeather view);

    @Provides
    public static WeatherService provideService(Retrofit retrofit) {
        return retrofit.create(WeatherService.class);
    }

    @Provides
    public static WeatherPresenter providePresenter(WeatherView view, WeatherService service) {
        return new WeatherPresenter(view, service);
    }

}