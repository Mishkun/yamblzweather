package com.kondenko.yamblzweather.ui.citysuggest;

import com.kondenko.yamblzweather.model.entity.City;
import com.kondenko.yamblzweather.model.entity.Prediction;
import com.kondenko.yamblzweather.model.service.CitiesSuggestService;
import com.kondenko.yamblzweather.ui.BaseInteractor;

import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

import static com.kondenko.yamblzweather.Const.JOB;
import static com.kondenko.yamblzweather.Const.UI;

/**
 * Created by Mishkun on 27.07.2017.
 */

public class FetchCityCoords extends BaseInteractor {
    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;
    private final CitiesSuggestService citiesSuggestService;
    private final LocationStore locationStore;

    public FetchCityCoords(@Named(JOB) Scheduler jobScheduler, @Named(UI) Scheduler uiScheduler, CitiesSuggestService citiesSuggestService,
                           LocationStore locationStore) {
        this.jobScheduler = jobScheduler;
        this.uiScheduler = uiScheduler;
        this.citiesSuggestService = citiesSuggestService;
        this.locationStore = locationStore;
    }

    public Completable getCityCoordinatesAndWrite(Prediction prediction) {
        return citiesSuggestService.getCityCoordinatesById(prediction.getId())
                                   .map((citySearchResult -> citySearchResult.getResult()
                                                                             .getGeometry()
                                                                             .getCoordinates()))
                                   .map(coord -> new City(coord, prediction.getPlace()))
                                   .flatMapCompletable(locationStore::setCurrentCity)
                                   .subscribeOn(jobScheduler)
                                   .observeOn(uiScheduler);
    }

}
