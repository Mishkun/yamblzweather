package com.kondenko.yamblzweather.ui.weather.dagger;

import com.kondenko.yamblzweather.job.AppJobCreator;
import com.kondenko.yamblzweather.model.service.WeatherService;
import com.kondenko.yamblzweather.ui.weather.JobsRepository;
import com.kondenko.yamblzweather.ui.weather.WeatherActivity;
import com.kondenko.yamblzweather.ui.weather.WeatherInteractor;
import com.kondenko.yamblzweather.ui.weather.WeatherJobsRepository;
import com.kondenko.yamblzweather.ui.weather.WeatherPresenter;
import com.kondenko.yamblzweather.ui.weather.WeatherView;
import com.kondenko.yamblzweather.utils.SettingsManager;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

import static com.kondenko.yamblzweather.dagger.modules.NetModule.OPEN_WEATHER_MAP_API;

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
    public AppJobCreator provideAppJobCreator(WeatherInteractor interactor, SettingsManager settingsManager) {
        return new AppJobCreator(interactor, settingsManager);
    }

    @Provides
    public JobsRepository provideJobsRepository(AppJobCreator appJobCreator) {
        return new WeatherJobsRepository(appJobCreator);
    }
}
