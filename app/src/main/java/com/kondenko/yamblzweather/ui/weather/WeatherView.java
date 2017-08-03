package com.kondenko.yamblzweather.ui.weather;

import com.kondenko.yamblzweather.data.weather.WeatherModel;
import com.kondenko.yamblzweather.ui.BaseView;

public interface WeatherView extends BaseView<WeatherModel> {

    public void showLatestUpdate(String latestUpdateTime);

}