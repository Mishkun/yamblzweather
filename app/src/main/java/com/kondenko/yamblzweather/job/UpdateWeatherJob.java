package com.kondenko.yamblzweather.job;


import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.kondenko.yamblzweather.model.entity.WeatherData;
import com.kondenko.yamblzweather.ui.weather.WeatherInteractor;
import com.kondenko.yamblzweather.utils.SettingsManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class UpdateWeatherJob extends Job {

    private WeatherInteractor interactor;
    private String cityId;
    private String units;
    private long refreshRateHr;

    // Do not delete, needed for job creation
    public static final String TAG = "UpdateWeaterJob";

    @Inject
    public UpdateWeatherJob(WeatherInteractor interactor, SettingsManager settingsManager) {
        this.interactor = interactor;
        this.cityId = settingsManager.getSelectedCity();
        this.units = settingsManager.getSelectedUnitValue();
        this.refreshRateHr = settingsManager.getRefreshRate();
    }

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        WeatherData data = interactor.getWeather(cityId, units).blockingGet();
        return data != null ? Result.SUCCESS : Result.FAILURE;
    }


    public void buildJobRequest(String name) {
        new JobRequest.Builder(UpdateWeatherJob.TAG)
                .setPeriodic(TimeUnit.HOURS.toMillis(refreshRateHr))
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setRequirementsEnforced(true)
                .setPersisted(true)
                .build()
                .schedule();
    }

}
