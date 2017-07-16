package com.kondenko.yamblzweather.job;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

import javax.inject.Inject;

public class AppJobCreator implements JobCreator {

    private UpdateWeatherJob job;

    @Inject
    public AppJobCreator(UpdateWeatherJob job) {
        this.job = job;
    }

    @Override
    public Job create(String tag) {
        if (tag.equals(UpdateWeatherJob.TAG)) return job;
        return null;
    }

}