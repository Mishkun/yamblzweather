package com.kondenko.yamblzweather.job;


import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.kondenko.yamblzweather.model.entity.WeatherData;
import com.kondenko.yamblzweather.ui.weather.WeatherInteractor;
import com.kondenko.yamblzweather.utils.Logger;
import com.kondenko.yamblzweather.utils.SettingsManager;

import javax.inject.Inject;

public class UpdateWeatherJob extends Job {

    // Do not delete, needed for job creation
    public static final String TAG = "UpdateWeatherJob";

    private WeatherInteractor interactor;
    private SettingsManager settingsManager;
    private String cityId;
    private String units;

    @Inject
    public UpdateWeatherJob(WeatherInteractor interactor, SettingsManager settingsManager) {
        this.interactor = interactor;
        this.settingsManager = settingsManager;
        cityId = settingsManager.getCity();
        units = settingsManager.getUnit();
    }

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        Logger.i(TAG, "Running job");
        WeatherData data = interactor.getWeather(cityId, units).blockingGet();
        long timeUpdated = System.currentTimeMillis();
        settingsManager.setLatestUpdate(timeUpdated);
        if (data != null) {
            Log.i(TAG, "Updated successfully");
            return Result.SUCCESS;
        } else {
            Log.i(TAG, "Updated with error");
            return Result.FAILURE;
        }
    }


    public void schedulePeriodicJob(long refreshRateMs) {
        new JobRequest.Builder(UpdateWeatherJob.TAG)
                .setPeriodic(refreshRateMs)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setRequirementsEnforced(true)
                .setUpdateCurrent(false)
                .setPersisted(true)
                .build()
                .schedule();
    }

}
