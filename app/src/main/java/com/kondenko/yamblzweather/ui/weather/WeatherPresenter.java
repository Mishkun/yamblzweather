package com.kondenko.yamblzweather.ui.weather;

import com.evernote.android.job.JobManager;
import com.kondenko.yamblzweather.Const;
import com.kondenko.yamblzweather.job.AppJobCreator;
import com.kondenko.yamblzweather.job.UpdateWeatherJob;
import com.kondenko.yamblzweather.ui.BasePresenter;
import com.kondenko.yamblzweather.utils.SettingsManager;
import com.kondenko.yamblzweather.utils.Utils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import retrofit2.Response;

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
        units = settingsManager.getUnitKey();
    }

    public void loadData() {
        scheduleUpdateJob();
        onCityChanged(Const.ID_MOSCOW); // Only show weather for Moscow (for Task 2)
    }

    public void onCityChanged(String cityId) {
        this.cityId = cityId;
        this.units = settingsManager.getUnitKey();
        settingsManager.setCity(cityId);
        if (isViewAttached()) getView().showLoading(true);
        getWeather();
    }

    public void onRefresh() {
        getWeather();
    }

    private void getWeather() {
        getInteractor().getWeather(cityId, units)
                .compose(bindToLifecycle())
                .doOnSuccess(response -> {
                    if (!Utils.isFromCache(response)) {
                        showUpdateTime(System.currentTimeMillis());
                    }
                })
                .doOnError(e -> {
                    long latestSavedUpdateTime = settingsManager.getLatestUpdateTime();
                    if (latestSavedUpdateTime > 0) showUpdateTime(latestSavedUpdateTime);
                })
                .doFinally(() -> {
                    if (isViewAttached()) getView().showLoading(false);
                })
                .map(Response::body)
                .subscribe(
                        result -> {
                            if (isViewAttached()) {
                                result.getMain().setTempUnitKey(units);
                                getView().setData(result);
                            }
                        },
                        error -> {
                            if (isViewAttached()) getView().showError(error);
                        });
    }

    private void scheduleUpdateJob() {
        JobManager.instance().addJobCreator(appJobCreator);
        int refreshRateHr = settingsManager.getRefreshRateHr();
        UpdateWeatherJob updateWeatherJob = new UpdateWeatherJob(getInteractor(), settingsManager);
        updateWeatherJob.schedulePeriodicJob(TimeUnit.HOURS.toMillis(refreshRateHr));
    }

    private void showUpdateTime(long time) {
        String latestUpdateTime = Utils.millisTo24time(time);
        if (isViewAttached()) getView().showLatestUpdate(latestUpdateTime);
    }

}
