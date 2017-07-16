package com.kondenko.yamblzweather.job;


import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

public class UpdateWeatherJob extends Job {

    // Do not delete, needed for job creation
    public static final String TAG = "UpdateWeatherJob";

    /*
    private WeatherInteractor interactor;
    private SettingsManager settingsManager;
    private String cityId;
    private String units;
    private static long refreshRate;

    @Inject
    public UpdateWeatherJob(WeatherInteractor interactor, SettingsManager settingsManager) {
        this.interactor = interactor;
        this.settingsManager = settingsManager;
        cityId = settingsManager.getSelectedCity();
        units = settingsManager.getSelectedUnitValue();
        refreshRate = settingsManager.getRefreshRate();
    }
    */

    public UpdateWeatherJob() {
    }

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        Log.i(TAG, "Running job");
        return Result.SUCCESS;
//        WeatherData data = interactor.getWeather(cityId, units).blockingGet();
//        long timeUpdated = System.currentTimeMillis();
//        return data != null ? Result.SUCCESS : Result.FAILURE;
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
