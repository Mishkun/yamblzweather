package com.kondenko.yamblzweather.ui.weather;

import com.kondenko.yamblzweather.Const;
import com.kondenko.yamblzweather.model.entity.WeatherModel;
import com.kondenko.yamblzweather.model.service.WeatherService;
import com.kondenko.yamblzweather.ui.BaseInteractor;
import com.kondenko.yamblzweather.utils.SettingsManager;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

import static com.kondenko.yamblzweather.Const.JOB;
import static com.kondenko.yamblzweather.Const.UI;

public class WeatherInteractor extends BaseInteractor {

    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;
    private WeatherService service;
    private SettingsManager settingsManager;

    @Inject
    public WeatherInteractor(@Named(JOB) Scheduler jobScheduler, @Named(UI) Scheduler uiScheduler, WeatherService weatherService,
                             SettingsManager settingsManager) {
        this.jobScheduler = jobScheduler;
        this.uiScheduler = uiScheduler;
        this.service = weatherService;
        this.settingsManager = settingsManager;
    }

    public Single<WeatherModel> getWeather(String cityId, String units) {
        Single<WeatherModel> weatherSingle = units.equals(Const.KEY_UNIT_TEMP_DEFAULT) ? service.getWeather(cityId) : service.getWeather(
                cityId, units);
        return weatherSingle
                .map(this::addTimestamp)
                .subscribeOn(jobScheduler)
                .observeOn(uiScheduler);
    }


    private WeatherModel addTimestamp(@NonNull WeatherModel result) throws Exception {
        long time = new Date().getTime();
        result.setTimestamp(time);
        settingsManager.setLatestUpdate(time);
        return result;

    }

}
