package com.kondenko.yamblzweather.job;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;


public class AppJobCreator implements JobCreator {

    @Inject
    public Map<String, Provider<Job>> jobs;

    @Override
    public Job create(String tag) {
        Provider<Job> jobProvider = jobs.get(tag);
        return jobProvider != null ? jobProvider.get() : null;
    }

}
