package com.kondenko.yamblzweather.ui.weather;

import com.kondenko.yamblzweather.model.WeatherService;
import com.kondenko.yamblzweather.ui.BasePresenter;

public class WeatherPresenter extends BasePresenter {

    private WeatherView view;
    private WeatherService service;

    public WeatherPresenter(WeatherView view, WeatherService service) {
        this.view = view;
        this.service = service;
    }
}
