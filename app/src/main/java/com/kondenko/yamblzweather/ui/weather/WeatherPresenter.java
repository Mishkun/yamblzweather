package com.kondenko.yamblzweather.ui.weather;

import com.kondenko.yamblzweather.domain.guards.JobsScheduler;
import com.kondenko.yamblzweather.domain.usecase.GetCurrentWeatherInteractor;
import com.kondenko.yamblzweather.ui.BasePresenter;
import com.kondenko.yamblzweather.infrastructure.SettingsManager;
import com.kondenko.yamblzweather.utils.Utils;

import javax.inject.Inject;

public class WeatherPresenter extends BasePresenter<WeatherView> {

    private final GetCurrentWeatherInteractor interactor;
    private final JobsScheduler weatherJobsScheduler;
    private final SettingsManager settingsManager;
    private String units;

    @Inject
    WeatherPresenter(GetCurrentWeatherInteractor interactor, JobsScheduler weatherJobsScheduler, SettingsManager settingsManager) {
        this.interactor = interactor;
        this.weatherJobsScheduler = weatherJobsScheduler;
        this.settingsManager = settingsManager;
        units = settingsManager.getUnitKey();
    }

    void loadData() {
        weatherJobsScheduler.scheduleUpdateJob(settingsManager.getRefreshRateHr());
        getWeather();
    }

    private void getWeather() {
        if (isViewAttached()) getView().showLoading(true);
        interactor.run(units)
                  .compose(bindToLifecycle())
                  .doFinally(() -> {
                      if (isViewAttached()) getView().showLoading(false);
                  })
                  .subscribe(
                          result -> {
                              if (isViewAttached()) {
                                  String unitReadable = settingsManager.getUnitValue();
                                  result.getMain().setTempUnitKey(unitReadable);
                                  getView().setData(result);
                                  showUpdateTime(result.getTimestamp());
                              }
                          },
                          error -> {
                              if (isViewAttached()) getView().showError(error);
                          });
    }

    private void showUpdateTime(long time) {
        String latestUpdateTime = Utils.millisTo24time(time);
        if (isViewAttached()) getView().showLatestUpdate(latestUpdateTime);
    }

}
