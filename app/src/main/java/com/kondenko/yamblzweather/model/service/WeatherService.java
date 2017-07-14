package com.kondenko.yamblzweather.model.service;

import com.kondenko.yamblzweather.model.entity.Weather;
import com.kondenko.yamblzweather.model.entity.WeatherData;

import io.reactivex.Single;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WeatherService {

    @POST("weather")
    public Single<WeatherData> getWeather(@Query("id") String id);

}
