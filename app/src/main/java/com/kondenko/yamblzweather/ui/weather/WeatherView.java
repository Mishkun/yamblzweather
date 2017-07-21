package com.kondenko.yamblzweather.ui.weather;

import com.kondenko.yamblzweather.model.entity.WeatherModel;
import com.kondenko.yamblzweather.ui.BaseView;

public interface WeatherView extends BaseView<WeatherModel> {

    public void showLatestUpdate(String latestUpdateTime);

}