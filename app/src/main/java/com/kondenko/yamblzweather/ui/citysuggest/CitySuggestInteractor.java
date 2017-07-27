package com.kondenko.yamblzweather.ui.citysuggest;

import com.kondenko.yamblzweather.model.entity.CitySuggest;
import com.kondenko.yamblzweather.model.entity.Prediction;
import com.kondenko.yamblzweather.model.service.CitiesSuggestService;
import com.kondenko.yamblzweather.ui.BaseInteractor;

import java.util.List;

import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.Single;

import static com.kondenko.yamblzweather.Const.JOB;
import static com.kondenko.yamblzweather.Const.UI;

/**
 * Created by Mishkun on 27.07.2017.
 */

public class CitySuggestInteractor extends BaseInteractor {
    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;
    private final CitiesSuggestService citiesSuggestService;

    public CitySuggestInteractor(@Named(JOB) Scheduler jobScheduler, @Named(UI) Scheduler uiScheduler, CitiesSuggestService citiesSuggestService) {
        this.jobScheduler = jobScheduler;
        this.uiScheduler = uiScheduler;
        this.citiesSuggestService = citiesSuggestService;
    }

    public Single<List<Prediction>> getCitySuggests(String query) {
        return citiesSuggestService.getSuggests(query, "(cities)")
                                   .map(CitySuggest::getPredictions)
                                   .subscribeOn(jobScheduler)
                                   .observeOn(uiScheduler);
    }

}
