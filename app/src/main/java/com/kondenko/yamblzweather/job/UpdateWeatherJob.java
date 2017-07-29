package com.kondenko.yamblzweather.job;


import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.kondenko.yamblzweather.model.entity.WeatherModel;
import com.kondenko.yamblzweather.ui.weather.WeatherInteractor;
import com.kondenko.yamblzweather.utils.SettingsManager;

import javax.inject.Inject;

public class UpdateWeatherJob extends Job {

    // Do not delete, needed for job creation
    public static final String TAG = "UpdateWeatherJob";

    private WeatherInteractor interactor;
    private SettingsManager settingsManager;
    private String units;

    @Inject
    public UpdateWeatherJob(WeatherInteractor interactor, SettingsManager settingsManager) {
        this.interactor = interactor;
        this.settingsManager = settingsManager;
        units = settingsManager.getUnitKey();
    }

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        WeatherModel weatherModel = interactor.getWeather(units).blockingGet();
        if (weatherModel != null) {
            long timeUpdated = System.currentTimeMillis();
            settingsManager.setLatestUpdate(timeUpdated);
            return Result.SUCCESS;
        } else {
            return Result.FAILURE;
        }
    }

    public static void schedulePeriodicJob(long refreshRateMs) {
        new JobRequest.Builder(UpdateWeatherJob.TAG)
                .setPeriodic(refreshRateMs)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setRequirementsEnforced(true)
                .setUpdateCurrent(true)
                .setPersisted(true)
                .build()
                .schedule();
    }

}
