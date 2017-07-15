package com.kondenko.yamblzweather.ui.weather;

import com.google.gson.JsonElement;
import com.kondenko.yamblzweather.Const;
import com.kondenko.yamblzweather.model.entity.WeatherData;
import com.kondenko.yamblzweather.model.service.WeatherService;
import com.kondenko.yamblzweather.ui.BaseInteractor;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.kondenko.yamblzweather.Const.KEY_UNIT_TEMP_DEFAULT;

public class WeatherInteractor extends BaseInteractor {

    private WeatherService service;

    @Inject
    public WeatherInteractor(WeatherService weatherService) {
        this.service = weatherService;
    }

    public Single<WeatherData> getWeather(String cityId, String units) {
        Single<WeatherData> weatherSingle = units.equals(Const.KEY_UNIT_TEMP_DEFAULT) ? service.getWeather(cityId) : service.getWeather(cityId, units);
        return weatherSingle
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
