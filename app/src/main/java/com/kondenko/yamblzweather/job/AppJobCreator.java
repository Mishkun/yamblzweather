package com.kondenko.yamblzweather.job;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class AppJobCreator implements JobCreator {

    @Override
    public Job create(String tag) {
        switch (tag) {
            case UpdateWeatherJob.TAG: return new UpdateWeatherJob();
            default: return null;
        }
    }

}