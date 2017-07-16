package com.kondenko.yamblzweather.ui.weather;

import com.kondenko.yamblzweather.Const;
import com.kondenko.yamblzweather.job.UpdateWeatherJob;
import com.kondenko.yamblzweather.ui.BasePresenter;
import com.kondenko.yamblzweather.utils.SettingsManager;

import javax.inject.Inject;

public class WeatherPresenter extends BasePresenter<WeatherView, WeatherInteractor> {

    private SettingsManager settingsManager;

    @Inject
    public WeatherPresenter(WeatherView view, WeatherInteractor interactor, SettingsManager settingsManager) {
        super(view, interactor);
        this.settingsManager = settingsManager;
    }

    @Override
    public void onAttach(WeatherView view) {
        super.onAttach(view);
        UpdateWeatherJob.schedulePeriodicJob(10000000);
        onCityChanged(Const.ID_MOSCOW); // Only show weather for Moscow (for Task 2)
    }

    public void onCityChanged(String id) {
        settingsManager.setSelectedCity(id);
        String units = settingsManager.getSelectedUnitKey();
        getWeather(id, units);
    }

    private void getWeather(String id, String units) {
        interactor.getWeather(id, units)
                .compose(bindToLifecycle())
                .subscribe(weather -> {
                            view.showCity(weather.getName());
                            view.showTemperature(weather.getMain().getTemp(), settingsManager.getSelectedUnitValue());
                            view.showCondition(weather.getWeather());
                        }, view::showError
                );
    }

}
