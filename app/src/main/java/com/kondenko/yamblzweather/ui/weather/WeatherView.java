package com.kondenko.yamblzweather.ui.weather;

import com.kondenko.yamblzweather.model.entity.WeatherData;
import com.kondenko.yamblzweather.ui.BaseView;

public interface WeatherView extends BaseView {

    public void showWeather(WeatherData weatherData);

}
