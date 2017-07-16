package com.kondenko.yamblzweather.ui.weather;

import com.evernote.android.job.JobCreator;
import com.evernote.android.job.JobManager;
import com.kondenko.yamblzweather.Const;
import com.kondenko.yamblzweather.job.AppJobCreator;
import com.kondenko.yamblzweather.job.UpdateWeatherJob;
import com.kondenko.yamblzweather.ui.BasePresenter;
import com.kondenko.yamblzweather.utils.SettingsManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class WeatherPresenter extends BasePresenter<WeatherView, WeatherInteractor> {

    private SettingsManager settingsManager;
    private AppJobCreator appJobCreator;

    @Inject
    public WeatherPresenter(WeatherView view, WeatherInteractor interactor, SettingsManager settingsManager, AppJobCreator appJobCreator) {
        super(view, interactor);
        this.settingsManager = settingsManager;
        this.appJobCreator = appJobCreator;
    }

    @Override
    public void onAttach(WeatherView view) {
        super.onAttach(view);
        scheduleUpdateJob();
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

    private void scheduleUpdateJob() {
        JobManager.instance().addJobCreator(appJobCreator);
        int refreshRateHr = settingsManager.getRefreshRateHr();
        UpdateWeatherJob updateWeatherJob = new UpdateWeatherJob(interactor, settingsManager);
        updateWeatherJob.schedulePeriodicJob(TimeUnit.HOURS.toMillis(refreshRateHr));
    }

}
