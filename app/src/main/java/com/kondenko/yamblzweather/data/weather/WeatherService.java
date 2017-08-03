package com.kondenko.yamblzweather.data.weather;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("weather")
    Single<WeatherModel> getWeather(@Query("lat") float lat, @Query("lon") float lon);

    @GET("weather")
    Single<WeatherModel> getWeather(@Query("lat") float lat, @Query("lon") float lon, @Query("units") String units);

}
