package com.kondenko.yamblzweather.ui.weather;

import android.util.Log;

import com.kondenko.yamblzweather.domain.usecase.GetCurrentCityInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetCurrentWeatherInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetFavoredCitiesInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetForecastInteractor;
import com.kondenko.yamblzweather.domain.usecase.SetCurrentCityInteractor;
import com.kondenko.yamblzweather.domain.usecase.UpdateWeatherInteractor;
import com.kondenko.yamblzweather.ui.BasePresenter;
import com.kondenko.yamblzweather.ui.weather.WeatherViewModel.CityList;
import com.kondenko.yamblzweather.utils.Utils;

import java.util.concurrent.CancellationException;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

public class WeatherPresenter extends BasePresenter<WeatherView> {

    private static final String TAG = WeatherPresenter.class.getSimpleName();
    private final GetCurrentWeatherInteractor currentWeatherInteractor;
    private final GetForecastInteractor getForecastInteractor;
    private final UpdateWeatherInteractor updateWeatherInteractor;
    private final SetCurrentCityInteractor setCurrentCityInteractor;
    private final GetCurrentCityInteractor getCurrentCityInteractor;
    private final GetFavoredCitiesInteractor getFavoredCitiesInteractor;

    private boolean initialized = false;

    @Inject
    WeatherPresenter(GetCurrentWeatherInteractor currentWeatherInteractor, GetForecastInteractor getForecastInteractor,
                     UpdateWeatherInteractor updateWeatherInteractor,
                     SetCurrentCityInteractor setCurrentCityInteractor, GetCurrentCityInteractor getCurrentCityInteractor,
                     GetFavoredCitiesInteractor getFavoredCitiesInteractor) {
        this.currentWeatherInteractor = currentWeatherInteractor;
        this.getForecastInteractor = getForecastInteractor;
        this.updateWeatherInteractor = updateWeatherInteractor;
        this.setCurrentCityInteractor = setCurrentCityInteractor;
        this.getCurrentCityInteractor = getCurrentCityInteractor;
        this.getFavoredCitiesInteractor = getFavoredCitiesInteractor;
    }

    @Override
    public void attachView(WeatherView view) {
        super.attachView(view);
        getWeatherViewModel().compose(bindToLifecycle())
                             .doOnNext(ignore -> {
                                 if (!initialized) {
                                     subscribeCitySelections(getView());
                                     initialized = true;
                                 }
                             })
                             .subscribe(result -> {
                                 if (isViewAttached()) {
                                     getView().setData(result);
                                     showUpdateTime(result.weather().timestamp());
                                 }
                             });
        getCurrentCityInteractor.run()
                                .doOnComplete(() -> {
                                    if (initialized) {
                                        subscribeCitySelections(getView());
                                        initialized = true;
                                    }
                                })
                                .subscribe();
    }

    @Override
    public void detachView() {
        super.detachView();
        initialized = false;
    }

    private void subscribeCitySelections(WeatherView view) {
        view.getCitySelections()
            .compose(bindToLifecycle())
            .flatMapCompletable(setCurrentCityInteractor::run)
            .subscribe();
    }

    private Observable<WeatherViewModel> getWeatherViewModel() {
        return currentWeatherInteractor.run()
                                       .flatMapMaybe(weather -> Maybe.zip(getFavoredCitiesInteractor.run().map(CityList::create).toMaybe(),
                                                                          getCurrentCityInteractor.run(),
                                                                          getForecastInteractor.run().doOnSuccess(forecast -> Log.d(TAG,
                                                                                                                                    "getWeatherViewModel" + forecast
                                                                                                                                            .toString())),
                                                                          (cities, city, forecast) -> WeatherViewModel.create(weather, forecast,
                                                                                                                              city,
                                                                                                                              cities)));
    }


    void updateData() {
        if (isViewAttached()) getView().showLoading(true);
        getCurrentCityInteractor.run()
                                .compose(bindToLifecycle())
                                .flatMapCompletable(updateWeatherInteractor::run)
                                .doFinally(() -> {
                                    if (isViewAttached()) getView().showLoading(false);
                                })
                                .doOnError(error -> {
                                    if (isViewAttached()) getView().showError(error);
                                })
                                .onErrorResumeNext(error -> {
                                    if (error instanceof CancellationException) return Completable.never();
                                    return Completable.error(error);
                                })
                                .onErrorComplete()
                                .subscribe();
    }

    private void showUpdateTime(long time) {
        String latestUpdateTime = Utils.millisTo24time(time);
        if (isViewAttached()) getView().showLatestUpdate(latestUpdateTime);
    }

}
