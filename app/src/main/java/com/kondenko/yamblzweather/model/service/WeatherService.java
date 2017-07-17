package com.kondenko.yamblzweather.model.service;

import com.google.gson.JsonElement;
import com.kondenko.yamblzweather.model.entity.WeatherData;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("weather")
    public Single<WeatherData> getWeather(@Query("id") String id);

    @GET("weather")
    public Single<WeatherData> getWeather(@Query("id") String id, @Query("units") String units);

}
