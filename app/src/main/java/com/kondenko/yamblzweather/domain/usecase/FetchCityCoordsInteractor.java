package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.domain.BaseInteractor;
import com.kondenko.yamblzweather.domain.entity.Prediction;
import com.kondenko.yamblzweather.domain.guards.CitySuggestProvider;
import com.kondenko.yamblzweather.domain.guards.LocationProvider;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

import static com.kondenko.yamblzweather.Const.JOB;
import static com.kondenko.yamblzweather.Const.UI;

/**
 * Created by Mishkun on 27.07.2017.
 */

public class FetchCityCoordsInteractor extends BaseInteractor {
    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;
    private final CitySuggestProvider citySuggestProvider;
    private final LocationProvider locationProvider;

    @Inject
    FetchCityCoordsInteractor(@Named(JOB) Scheduler jobScheduler, @Named(UI) Scheduler uiScheduler, CitySuggestProvider citiesSuggestProvider,
                              LocationProvider locationProvider) {
        this.jobScheduler = jobScheduler;
        this.uiScheduler = uiScheduler;
        this.citySuggestProvider = citiesSuggestProvider;
        this.locationProvider = locationProvider;
    }

    public Completable run(Prediction prediction) {
        return citySuggestProvider.getCityFromPrediction(prediction)
                                  .flatMapCompletable(city -> Completable.concatArray(locationProvider.addFavoredCity(city),
                                                                                      locationProvider.setCurrentCity(city)))
                                  .subscribeOn(jobScheduler)
                                  .observeOn(uiScheduler);
    }

}
