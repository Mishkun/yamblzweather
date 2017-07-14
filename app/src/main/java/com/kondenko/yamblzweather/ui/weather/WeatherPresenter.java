package com.kondenko.yamblzweather.ui.weather;

import com.kondenko.yamblzweather.model.entity.Weather;
import com.kondenko.yamblzweather.model.service.WeatherService;
import com.kondenko.yamblzweather.ui.BasePresenter;
import com.trello.rxlifecycle2.RxLifecycle;

import javax.inject.Inject;

public class WeatherPresenter extends BasePresenter<WeatherView, WeatherInteractor> {

    @Inject
    public WeatherPresenter(WeatherView view, WeatherInteractor interactor) {
        super(view, interactor);
    }

    @Override
    public void onAttach(WeatherView view) {
        super.onAttach(view);
        onCityChanged("524901"); // Only show weather for Moscow (for Task 2)
    }

    public void onCityChanged(String id) {
        interactor.getWeather(id)
                .compose(bindToLifecycle())
                .subscribe(view::showWeather, view::showError);
    }


}
