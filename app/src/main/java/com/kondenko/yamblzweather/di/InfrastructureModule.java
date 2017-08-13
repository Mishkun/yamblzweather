package com.kondenko.yamblzweather.di;

import com.kondenko.yamblzweather.App;
import com.kondenko.yamblzweather.domain.guards.JobsScheduler;
import com.kondenko.yamblzweather.infrastructure.SettingsManager;
import com.kondenko.yamblzweather.infrastructure.WeatherJobsScheduler;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Mishkun on 13.08.2017.
 */
@Module
abstract class InfrastructureModule {
    @Provides
    @Singleton
    static SettingsManager provideSettingsManager(App application) {
        return new SettingsManager(application);
    }

    @Binds
    @Singleton
    abstract JobsScheduler provideJobsRepository(WeatherJobsScheduler weatherJobsScheduler);

}
