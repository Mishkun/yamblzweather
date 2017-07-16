package com.kondenko.yamblzweather.ui.weather.dagger;

import android.content.Context;

import com.evernote.android.job.Job;
import com.kondenko.yamblzweather.job.AppJobCreator;
import com.kondenko.yamblzweather.job.UpdateWeatherJob;
import com.kondenko.yamblzweather.model.entity.Weather;
import com.kondenko.yamblzweather.model.service.WeatherService;
import com.kondenko.yamblzweather.ui.weather.WeatherActivity;
import com.kondenko.yamblzweather.ui.weather.WeatherInteractor;
import com.kondenko.yamblzweather.ui.weather.WeatherPresenter;
import com.kondenko.yamblzweather.ui.weather.WeatherView;
import com.kondenko.yamblzweather.utils.SettingsManager;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import retrofit2.Retrofit;

@Module
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
    public WeatherPresenter providePresenter(WeatherView view, WeatherInteractor interactor, SettingsManager settingsManager, AppJobCreator jobCreator) {
        return new WeatherPresenter(view, interactor, settingsManager, jobCreator);
    }

    @Provides
    public UpdateWeatherJob provideUpdateWeatherJob(WeatherInteractor interactor, SettingsManager settingsManager) {
        return new UpdateWeatherJob(interactor, settingsManager);
    }

    @Provides
    public AppJobCreator provideAppJobCreator(UpdateWeatherJob updateWeatherJob) {
        return new AppJobCreator(updateWeatherJob);
    }

}
