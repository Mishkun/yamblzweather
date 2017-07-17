package com.kondenko.yamblzweather.ui.weather;

import com.evernote.android.job.JobManager;
import com.kondenko.yamblzweather.Const;
import com.kondenko.yamblzweather.job.AppJobCreator;
import com.kondenko.yamblzweather.job.UpdateWeatherJob;
import com.kondenko.yamblzweather.model.entity.WeatherData;
import com.kondenko.yamblzweather.ui.BasePresenter;
import com.kondenko.yamblzweather.utils.SettingsManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class WeatherPresenter extends BasePresenter<WeatherView, WeatherInteractor> {

    private SettingsManager settingsManager;
    private AppJobCreator appJobCreator;
    private String cityId;
    private String units;

    @Inject
    public WeatherPresenter(WeatherView view, WeatherInteractor interactor, SettingsManager settingsManager, AppJobCreator appJobCreator) {
        super(view, interactor);
        this.settingsManager = settingsManager;
        this.appJobCreator = appJobCreator;
        cityId = settingsManager.getCity();
        units = settingsManager.getUnit();
    }

    @Override
    public void onAttach(WeatherView view) {
        super.onAttach(view);
        scheduleUpdateJob();
        onCityChanged(Const.ID_MOSCOW); // Only show weather for Moscow (for Task 2)
    }

    public void onCityChanged(String id) {
        settingsManager.setCity(id);
        String units = settingsManager.setUnit();
        getWeather(id, units);
    }

    public void onRefresh() {
        view.showLoading(true);
        getWeather(cityId, units);
    }

    private void getWeather(String id, String units) {
        interactor.getWeather(id, units)
                .compose(bindToLifecycle())
                .doFinally(() -> view.showLoading(false))
                .subscribe(this::displayData, view::showError);
    }

    private void displayData(WeatherData weather) {
        view.showCity(weather.getName());
        view.showTemperature(weather.getMain().getTemp(), settingsManager.getUnit());
        view.showCondition(weather.getWeather());
    }

    private void scheduleUpdateJob() {
        JobManager.instance().addJobCreator(appJobCreator);
        int refreshRateHr = settingsManager.getRefreshRateHr();
        UpdateWeatherJob updateWeatherJob = new UpdateWeatherJob(interactor, settingsManager);
        updateWeatherJob.schedulePeriodicJob(TimeUnit.HOURS.toMillis(refreshRateHr));
    }

}
