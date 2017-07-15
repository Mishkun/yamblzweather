package com.kondenko.yamblzweather.ui.weather;

import com.kondenko.yamblzweather.model.entity.Weather;
import com.kondenko.yamblzweather.ui.BaseView;

import java.util.List;

public interface WeatherView extends BaseView {

    @Deprecated
    public void showCity(String weatherData);

    public void showTemperature(double temp, String units);

    public void showCondition(List<Weather> condition);

}
