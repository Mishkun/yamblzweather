package com.kondenko.yamblzweather.model.service;

import com.kondenko.yamblzweather.model.entity.WeatherModel;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("weather")
    public Single<Response<WeatherModel>> getWeather(@Query("id") String id);

    @GET("weather")
    public Single<Response<WeatherModel>> getWeather(@Query("id") String id, @Query("units") String units);

}
