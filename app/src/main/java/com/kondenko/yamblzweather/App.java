package com.kondenko.yamblzweather;

import android.app.Activity;
import android.app.Application;

import com.evernote.android.job.JobManager;
import com.kondenko.yamblzweather.dagger.components.DaggerAppComponent;
import com.kondenko.yamblzweather.dagger.modules.AppModule;
import com.kondenko.yamblzweather.dagger.modules.NetModule;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;


public class App extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) return;
        LeakCanary.install(this);
        JobManager.create(this);
        DaggerAppComponent.builder()
                .application(this)
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }

}
