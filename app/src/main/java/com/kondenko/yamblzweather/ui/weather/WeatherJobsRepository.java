package com.kondenko.yamblzweather.ui.weather;

import com.evernote.android.job.JobCreator;
import com.evernote.android.job.JobManager;
import com.kondenko.yamblzweather.job.UpdateWeatherJob;

import java.util.concurrent.TimeUnit;

public class WeatherJobsRepository implements JobsRepository {

    public WeatherJobsRepository(JobCreator appJobCreator) {
        JobManager.instance().addJobCreator(appJobCreator);
    }

    public void scheduleUpdateJob(int refreshRateHr) {
        UpdateWeatherJob.schedulePeriodicJob(TimeUnit.HOURS.toMillis(refreshRateHr));
    }
}