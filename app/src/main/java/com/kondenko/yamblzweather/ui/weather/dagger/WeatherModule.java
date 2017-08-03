package com.kondenko.yamblzweather.ui.weather.dagger;

import com.kondenko.yamblzweather.domain.guards.JobsScheduler;
import com.kondenko.yamblzweather.domain.usecase.GetCurrentWeatherInteractor;
import com.kondenko.yamblzweather.infrastructure.AppJobCreator;
import com.kondenko.yamblzweather.data.weather.WeatherService;
import com.kondenko.yamblzweather.ui.weather.WeatherActivity;
import com.kondenko.yamblzweather.infrastructure.WeatherJobsScheduler;
import com.kondenko.yamblzweather.ui.weather.WeatherView;
import com.kondenko.yamblzweather.infrastructure.SettingsManager;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

import static com.kondenko.yamblzweather.di.modules.NetModule.OPEN_WEATHER_MAP_API;

@Module
public class WeatherModule {

    @Provides
    public WeatherView provideView(WeatherActivity activity) {
        return activity;
    }

    @Provides
    public WeatherService provideWeatherService(@Named(OPEN_WEATHER_MAP_API) Retrofit retrofit) {
        return retrofit.create(WeatherService.class);
    }

    @Provides
    public AppJobCreator provideAppJobCreator(GetCurrentWeatherInteractor interactor, SettingsManager settingsManager) {
        return new AppJobCreator(interactor, settingsManager);
    }

    @Provides
    public JobsScheduler provideJobsRepository(AppJobCreator appJobCreator) {
        return new WeatherJobsScheduler(appJobCreator);
    }
}
