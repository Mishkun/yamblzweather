package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.guards.LocationProvider;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.Single;

import static com.kondenko.yamblzweather.Const.JOB;
import static com.kondenko.yamblzweather.Const.UI;

/**
 * Created by Mishkun on 05.08.2017.
 */

public class GetFavoredCitiesInteractor {
    private final LocationProvider locationProvider;
    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;

    @Inject
    GetFavoredCitiesInteractor(LocationProvider locationProvider, @Named(JOB) Scheduler jobScheduler, @Named(UI) Scheduler uiScheduler) {
        this.locationProvider = locationProvider;
        this.jobScheduler = jobScheduler;
        this.uiScheduler = uiScheduler;
    }

    public Single<List<City>> run() {
        return locationProvider.getFavoriteCities()
                               .subscribeOn(jobScheduler)
                               .observeOn(uiScheduler);
    }
}
