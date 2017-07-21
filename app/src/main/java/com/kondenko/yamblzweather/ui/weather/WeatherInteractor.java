package com.kondenko.yamblzweather.ui.weather;

import com.kondenko.yamblzweather.Const;
import com.kondenko.yamblzweather.model.entity.WeatherModel;
import com.kondenko.yamblzweather.model.service.WeatherService;
import com.kondenko.yamblzweather.ui.BaseInteractor;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class WeatherInteractor extends BaseInteractor {

    private WeatherService service;

    @Inject
    public WeatherInteractor(WeatherService weatherService) {
        this.service = weatherService;
    }

    public Single<Response<WeatherModel>> getWeather(String cityId, String units) {
        Single<Response<WeatherModel>> weatherSingle = units.equals(Const.KEY_UNIT_TEMP_DEFAULT) ? service.getWeather(cityId) : service.getWeather(cityId, units);
        return weatherSingle
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
