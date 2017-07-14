package com.kondenko.yamblzweather.ui.weather.dagger;

import com.kondenko.yamblzweather.model.entity.Weather;
import com.kondenko.yamblzweather.model.service.WeatherService;
import com.kondenko.yamblzweather.ui.weather.WeatherActivity;
import com.kondenko.yamblzweather.ui.weather.WeatherInteractor;
import com.kondenko.yamblzweather.ui.weather.WeatherPresenter;
import com.kondenko.yamblzweather.ui.weather.WeatherView;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module// (subcomponents = WeatherSubcomponent.class)
public class WeatherModule {

    @Provides
    public WeatherView provideView(WeatherActivity activity) {
        return activity;
    }

    @Provides
    public WeatherService provideWeatherService(Retrofit retrofit) {
        return retrofit.create(WeatherService.class);
    }

    @Provides
    public WeatherInteractor provideInteractor(WeatherService service) {
        return new WeatherInteractor(service);
    }

    @Provides
    public WeatherPresenter providePresenter(WeatherView view, WeatherInteractor interactor) {
        return new WeatherPresenter(view, interactor);
    }

}
