package com.kondenko.yamblzweather.model.service;

import com.kondenko.yamblzweather.model.entity.CitySearchResult;
import com.kondenko.yamblzweather.model.entity.CitySuggest;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mishkun on 27.07.2017.
 */

public interface CitiesSuggestService {
    @GET("autocomplete/json")
    Single<CitySuggest> getSuggests(@Query("input") String input, @Query("types") String types);

    @GET("details/json")
    Single<CitySearchResult> getCityCoordinatesById(@Query("placeid") String id);
}
