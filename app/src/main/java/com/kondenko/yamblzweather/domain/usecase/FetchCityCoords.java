package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Prediction;
import com.kondenko.yamblzweather.domain.guards.CitySuggestProvider;
import com.kondenko.yamblzweather.domain.guards.LocationProvider;
import com.kondenko.yamblzweather.domain.BaseInteractor;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.Single;

import static com.kondenko.yamblzweather.Const.JOB;
import static com.kondenko.yamblzweather.Const.UI;

/**
 * Created by Mishkun on 27.07.2017.
 */

public class FetchCityCoords extends BaseInteractor {
    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;
    private final CitiesSuggestService citiesSuggestService;
    private final LocationProvider locationProvider;

    @Inject
    public FetchCityCoords(@Named(JOB) Scheduler jobScheduler, @Named(UI) Scheduler uiScheduler, CitiesSuggestService citiesSuggestService,
                           LocationProvider locationProvider) {
        this.jobScheduler = jobScheduler;
        this.uiScheduler = uiScheduler;
        this.citiesSuggestService = citiesSuggestService;
        this.locationProvider = locationProvider;
    }

    public Completable getCityCoordinatesAndWrite(Prediction prediction) {
        return citiesSuggestService.getCityCoordinatesById(prediction.getId())
                                   .map((citySearchResult -> citySearchResult.getResult()
                                                                             .getGeometry()
                                                                             .getCoordinates()))
                                   .map(coord -> new CityResponse(coord, prediction.getPlace()))
                                   .flatMapCompletable(locationProvider::setCurrentCity)
                                   .subscribeOn(jobScheduler)
                                   .observeOn(uiScheduler);
    }

}
