package com.kondenko.yamblzweather.infrastructure;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;
import com.kondenko.yamblzweather.domain.usecase.GetCityInteractor;
import com.kondenko.yamblzweather.domain.usecase.UpdateWeatherInteractor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppJobCreator implements JobCreator {

    private final GetCityInteractor getCityInteractor;
    private UpdateWeatherInteractor updateWeatherInteractor;
    private SettingsManager settingsManager;

    @Inject
    public AppJobCreator(UpdateWeatherInteractor updateWeatherInteractor, GetCityInteractor getCityInteractor, SettingsManager settingsManager) {
        this.updateWeatherInteractor = updateWeatherInteractor;
        this.getCityInteractor = getCityInteractor;
        this.settingsManager = settingsManager;
    }

    @Override
    public Job create(String tag) {
        if (tag.equals(UpdateWeatherJob.TAG)) return new UpdateWeatherJob(getCityInteractor, updateWeatherInteractor, settingsManager);
        return null;
    }

}