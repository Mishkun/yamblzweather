package com.kondenko.yamblzweather.dagger.modules;

import android.app.Application;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.kondenko.yamblzweather.job.AppJobCreator;
import com.kondenko.yamblzweather.job.UpdateWeatherJob;
import com.kondenko.yamblzweather.ui.weather.WeatherInteractor;
import com.kondenko.yamblzweather.utils.SettingsManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;

@Module
public class JobsModule {

    @Provides
    @Singleton
    public JobManager provideJobManager(Application app, AppJobCreator creator) {
        JobManager.create(app).addJobCreator(creator);
        return JobManager.instance();
    }

    @Provides
    @IntoMap
    @StringKey(UpdateWeatherJob.TAG)
    public Job provideUpdateWeatherJob(WeatherInteractor interactor, SettingsManager settingsManager) {
        return new UpdateWeatherJob(interactor, settingsManager);
    }

}
