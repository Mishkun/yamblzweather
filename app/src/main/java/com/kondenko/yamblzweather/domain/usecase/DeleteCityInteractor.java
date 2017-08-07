package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.domain.BaseInteractor;
import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.guards.LocationProvider;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

import static com.kondenko.yamblzweather.Const.JOB;
import static com.kondenko.yamblzweather.Const.UI;

/**
 * Created by Mishkun on 05.08.2017.
 */

public class DeleteCityInteractor extends BaseInteractor {
    private final LocationProvider locationProvider;
    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;

    @Inject
    DeleteCityInteractor(LocationProvider locationProvider, @Named(JOB) Scheduler jobScheduler, @Named(UI) Scheduler uiScheduler) {
        this.locationProvider = locationProvider;
        this.jobScheduler = jobScheduler;
        this.uiScheduler = uiScheduler;

    }

    public Completable run(City city) {
        return locationProvider.getCurrentCity()
                               .flatMapCompletable(currentCity -> !currentCity.equals(city)
                                       ? locationProvider.deleteFavoriteCity(city)
                                       : Completable.complete())
                               .subscribeOn(jobScheduler)
                               .observeOn(uiScheduler);
    }
}
