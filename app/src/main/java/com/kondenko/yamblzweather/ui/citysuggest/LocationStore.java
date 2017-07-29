package com.kondenko.yamblzweather.ui.citysuggest;

import com.kondenko.yamblzweather.model.entity.City;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Mishkun on 27.07.2017.
 */

public interface LocationStore {
    Single<City> getCurrentCity();
    Completable setCurrentCity(City city);
}
