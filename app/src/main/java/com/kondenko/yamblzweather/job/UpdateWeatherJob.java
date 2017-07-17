package com.kondenko.yamblzweather.job;


import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.kondenko.yamblzweather.model.entity.Weather;
import com.kondenko.yamblzweather.model.entity.WeatherData;
import com.kondenko.yamblzweather.ui.weather.WeatherInteractor;
import com.kondenko.yamblzweather.utils.Logger;
import com.kondenko.yamblzweather.utils.SettingsManager;
import com.kondenko.yamblzweather.utils.StorageManager;

import javax.inject.Inject;

import retrofit2.Response;

import static android.R.attr.data;

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
        WeatherData weatherData = interactor.getWeather(cityId, units).blockingGet();
        if (weatherData != null) {
            long timeUpdated = System.currentTimeMillis();
            settingsManager.setLatestUpdate(timeUpdated);
            return Result.SUCCESS;
        } else {
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
