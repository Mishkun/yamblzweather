package com.kondenko.yamblzweather.job;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;
import com.kondenko.yamblzweather.ui.weather.WeatherInteractor;
import com.kondenko.yamblzweather.utils.SettingsManager;

import javax.inject.Inject;

public class AppJobCreator implements JobCreator {

    private WeatherInteractor interactor;
    private SettingsManager settingsManager;

    @Inject
    public AppJobCreator(WeatherInteractor interactor, SettingsManager settingsManager) {
        this.interactor = interactor;
        this.settingsManager = settingsManager;
    }

    @Override
    public Job create(String tag) {
        if (tag.equals(UpdateWeatherJob.TAG)) return new UpdateWeatherJob(interactor, settingsManager);
        return null;
    }

}