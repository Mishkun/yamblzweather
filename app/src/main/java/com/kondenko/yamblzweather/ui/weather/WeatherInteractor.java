package com.kondenko.yamblzweather.ui.weather;

import com.kondenko.yamblzweather.Const;
import com.kondenko.yamblzweather.model.entity.WeatherModel;
import com.kondenko.yamblzweather.model.service.WeatherService;
import com.kondenko.yamblzweather.ui.BaseInteractor;
import com.kondenko.yamblzweather.utils.SettingsManager;
import com.kondenko.yamblzweather.utils.Utils;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class WeatherInteractor extends BaseInteractor {

    private WeatherService service;
    private SettingsManager settingsManager;

    @Inject
    public WeatherInteractor(WeatherService weatherService) {
        this.service = weatherService;
    }

    public Single<WeatherModel> getWeather(String cityId, String units) {
        Single<Response<WeatherModel>> weatherSingle = units.equals(Const.KEY_UNIT_TEMP_DEFAULT) ? service.getWeather(cityId) : service.getWeather(
                cityId, units);
        return weatherSingle
                .map(this::unwrapResponse)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


    private WeatherModel unwrapResponse(@NonNull Response<WeatherModel> response) throws Exception {
        if (!Utils.isFromCache(response)) {
            WeatherModel result = response.body();
            long timestamp = System.currentTimeMillis();
            if (result != null) {
                result.setTimestamp(timestamp);
            }
            settingsManager.setLatestUpdate(timestamp);
            return result;
        } else {
            WeatherModel result = response.body();
            if (result != null) {
                result.setTimestamp(settingsManager.getLatestUpdateTime());
            }
            return result;
        }
    }

}
