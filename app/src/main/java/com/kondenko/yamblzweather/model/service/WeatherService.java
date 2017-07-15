package com.kondenko.yamblzweather.model.service;

import com.google.gson.JsonElement;
import com.kondenko.yamblzweather.model.entity.WeatherData;

import io.reactivex.Single;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WeatherService {

    @POST("weather")
    public Single<WeatherData> getWeather(@Query("id") java.lang.String id);

    @POST("weather")
    public Single<WeatherData> getWeather(@Query("id") java.lang.String id, @Query("units") java.lang.String units);

}
