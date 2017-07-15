package com.kondenko.yamblzweather.ui.weather;

import com.kondenko.yamblzweather.model.entity.WeatherData;
import com.kondenko.yamblzweather.model.service.WeatherService;
import com.kondenko.yamblzweather.ui.BaseInteractor;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeatherInteractor extends BaseInteractor {

    private WeatherService service;

    @Inject
    public WeatherInteractor(WeatherService weatherService) {
        this.service = weatherService;
    }

    public Single<WeatherData> getWeather(java.lang.String id, java.lang.String units) {
        // An empty string represents the default value (Kelvin)
        Single<WeatherData> weatherSingle = units.length() == 0 ? service.getWeather(id) : service.getWeather(id, units);
        return weatherSingle
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
