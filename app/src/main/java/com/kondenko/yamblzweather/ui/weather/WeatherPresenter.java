package com.kondenko.yamblzweather.ui.weather;

import com.kondenko.yamblzweather.ui.BasePresenter;
import com.kondenko.yamblzweather.utils.SettingsManager;
import com.kondenko.yamblzweather.utils.Utils;

import javax.inject.Inject;

public class WeatherPresenter extends BasePresenter<WeatherView> {

    private final WeatherInteractor interactor;
    private final JobsRepository weatherJobsRepository;
    private final SettingsManager settingsManager;
    private String units;

    @Inject
    public WeatherPresenter(WeatherInteractor interactor, JobsRepository weatherJobsRepository, SettingsManager settingsManager) {
        this.interactor = interactor;
        this.weatherJobsRepository = weatherJobsRepository;
        this.settingsManager = settingsManager;
        units = settingsManager.getUnitKey();
    }

    public void loadData() {
        weatherJobsRepository.scheduleUpdateJob(settingsManager.getRefreshRateHr());
        getWeather();
    }

    private void getWeather() {
        if (isViewAttached()) getView().showLoading(true);
        interactor.getWeather(units)
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
