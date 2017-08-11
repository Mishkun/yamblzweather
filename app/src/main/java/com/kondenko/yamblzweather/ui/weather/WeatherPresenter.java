package com.kondenko.yamblzweather.ui.weather;

import com.kondenko.yamblzweather.domain.usecase.GetCurrentCityInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetCurrentWeatherInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetFavoredCitiesInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetForecastInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetUnitsInteractor;
import com.kondenko.yamblzweather.domain.usecase.SetCurrentCityInteractor;
import com.kondenko.yamblzweather.domain.usecase.UpdateWeatherInteractor;
import com.kondenko.yamblzweather.ui.BasePresenter;

import java.util.concurrent.CancellationException;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Maybe;

@Singleton
public class WeatherPresenter extends BasePresenter<WeatherView> {

    private static final String TAG = WeatherPresenter.class.getSimpleName();
    private final GetCurrentWeatherInteractor currentWeatherInteractor;
    private final GetForecastInteractor getForecastInteractor;
    private final UpdateWeatherInteractor updateWeatherInteractor;
    private final SetCurrentCityInteractor setCurrentCityInteractor;
    private final GetCurrentCityInteractor getCurrentCityInteractor;
    private final GetFavoredCitiesInteractor getFavoredCitiesInteractor;
    private final GetUnitsInteractor getUnitsInteractor;

    private boolean initialized = false;

    @Inject
    WeatherPresenter(GetCurrentWeatherInteractor currentWeatherInteractor, GetForecastInteractor getForecastInteractor,
                     UpdateWeatherInteractor updateWeatherInteractor,
                     SetCurrentCityInteractor setCurrentCityInteractor, GetCurrentCityInteractor getCurrentCityInteractor,
                     GetFavoredCitiesInteractor getFavoredCitiesInteractor, GetUnitsInteractor getUnitsInteractor) {
        this.currentWeatherInteractor = currentWeatherInteractor;
        this.getForecastInteractor = getForecastInteractor;
        this.updateWeatherInteractor = updateWeatherInteractor;
        this.setCurrentCityInteractor = setCurrentCityInteractor;
        this.getCurrentCityInteractor = getCurrentCityInteractor;
        this.getFavoredCitiesInteractor = getFavoredCitiesInteractor;
        this.getUnitsInteractor = getUnitsInteractor;
    }

    @Override
    public void attachView(WeatherView view) {
        super.attachView(view);
        getCurrentCityInteractor.run()
                                .flatMapMaybe(city -> Maybe.zip(getFavoredCitiesInteractor.run().toMaybe(),
                                                                currentWeatherInteractor.run(),
                                                                getForecastInteractor.run(),
                                                                getUnitsInteractor.run().toMaybe(),
                                                                (cities, weather, forecast, unit) -> WeatherViewModel.create(weather, forecast,
                                                                                                                             city,
                                                                                                                             cities, unit)))
                                .compose(bindToLifecycle())
                                .doOnNext(ignore -> {
                                    if (!initialized) {
                                        subscribeCitySelections(getView());
                                        initialized = true;
                                    }
                                })
                                .subscribe(result -> {
                                               if (isViewAttached()) {
                                                   getView().setData(result);
                                               }
                                           },
                                           throwable -> {
                                               if (isViewAttached()) {
                                                   getView().showError(throwable);
                                               }
                                           });
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


    void updateData() {
        if (isViewAttached()) getView().showLoading(true);
        getCurrentCityInteractor.run()
                                .firstElement()
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

}
