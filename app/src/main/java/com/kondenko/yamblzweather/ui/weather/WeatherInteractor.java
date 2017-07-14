package com.kondenko.yamblzweather.ui.weather;

import com.kondenko.yamblzweather.model.entity.Weather;
import com.kondenko.yamblzweather.model.entity.WeatherData;
import com.kondenko.yamblzweather.model.service.WeatherService;
import com.kondenko.yamblzweather.ui.BaseInteractor;

import java.util.Observable;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeatherInteractor extends BaseInteractor {

    private WeatherService weatherService;

    @Inject
    public WeatherInteractor(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public Single<WeatherData> getWeather(String id) {
       return weatherService.getWeather(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
