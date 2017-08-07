package com.kondenko.yamblzweather.ui.weather;

import com.kondenko.yamblzweather.domain.usecase.GetCurrentCityInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetCurrentWeatherInteractor;
import com.kondenko.yamblzweather.domain.usecase.UpdateWeatherInteractor;
import com.kondenko.yamblzweather.ui.BasePresenter;
import com.kondenko.yamblzweather.utils.Utils;

import javax.inject.Inject;

public class WeatherPresenter extends BasePresenter<WeatherView> {

    private static final String TAG = WeatherPresenter.class.getSimpleName();
    private final GetCurrentWeatherInteractor currentWeatherInteractor;

    private final UpdateWeatherInteractor updateWeatherInteractor;
    private final GetCurrentCityInteractor getCurrentCityInteractor;

    @Inject
    WeatherPresenter(GetCurrentWeatherInteractor currentWeatherInteractor, UpdateWeatherInteractor updateWeatherInteractor,
                     GetCurrentCityInteractor getCurrentCityInteractor) {
        this.currentWeatherInteractor = currentWeatherInteractor;
        this.updateWeatherInteractor = updateWeatherInteractor;
        this.getCurrentCityInteractor = getCurrentCityInteractor;
    }

    @Override
    public void attachView(WeatherView view) {
        super.attachView(view);
        currentWeatherInteractor.run()
                                .flatMapMaybe(weather -> getCurrentCityInteractor.run().map(city -> WeatherViewModel.create(weather, city)))
                                .compose(bindToLifecycle())
                                .subscribe(result -> {
                                    if (isViewAttached()) {
                                        getView().setData(result);
                                        showUpdateTime(result.weather().timestamp());
                                    }
                                });
    }


    void updateData() {
        if (isViewAttached()) getView().showLoading(true);
        getCurrentCityInteractor.run()
                                .flatMapCompletable(updateWeatherInteractor::run)
                                .doFinally(() -> {
                                    if (isViewAttached()) getView().showLoading(false);
                                })
                                .doOnError(error -> {
                                    if (isViewAttached()) getView().showError(error);
                                })
                                .onErrorComplete()
                                .compose(bindToLifecycle())
                                .subscribe();
    }

    private void showUpdateTime(long time) {
        String latestUpdateTime = Utils.millisTo24time(time);
        if (isViewAttached()) getView().showLatestUpdate(latestUpdateTime);
    }

}
