package com.kondenko.yamblzweather.ui.weather;

import com.kondenko.yamblzweather.domain.usecase.GetCurrentCityInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetCurrentWeatherInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetFavoredCitiesInteractor;
import com.kondenko.yamblzweather.domain.usecase.SetCurrentCityInteractor;
import com.kondenko.yamblzweather.domain.usecase.UpdateWeatherInteractor;
import com.kondenko.yamblzweather.ui.BasePresenter;
import com.kondenko.yamblzweather.utils.Utils;

import java.util.concurrent.CancellationException;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class WeatherPresenter extends BasePresenter<WeatherView> {

    private static final String TAG = WeatherPresenter.class.getSimpleName();
    private final GetCurrentWeatherInteractor currentWeatherInteractor;

    private final UpdateWeatherInteractor updateWeatherInteractor;
    private final SetCurrentCityInteractor setCurrentCityInteractor;
    private final GetCurrentCityInteractor getCurrentCityInteractor;
    private final GetFavoredCitiesInteractor getFavoredCitiesInteractor;

    @Inject
    WeatherPresenter(GetCurrentWeatherInteractor currentWeatherInteractor, UpdateWeatherInteractor updateWeatherInteractor,
                     SetCurrentCityInteractor setCurrentCityInteractor, GetCurrentCityInteractor getCurrentCityInteractor,
                     GetFavoredCitiesInteractor getFavoredCitiesInteractor) {
        this.currentWeatherInteractor = currentWeatherInteractor;
        this.updateWeatherInteractor = updateWeatherInteractor;
        this.setCurrentCityInteractor = setCurrentCityInteractor;
        this.getCurrentCityInteractor = getCurrentCityInteractor;
        this.getFavoredCitiesInteractor = getFavoredCitiesInteractor;
    }

    @Override
    public void attachView(WeatherView view) {
        super.attachView(view);
        getWeatherViewModel().compose(bindToLifecycle())
                             .subscribe(result -> {
                                 if (isViewAttached()) {
                                     getView().setData(result);
                                     showUpdateTime(result.weather().timestamp());
                                     subscribeCitySelections(getView());
                                 }
                             });
        getCurrentCityInteractor.run().doOnComplete(() -> subscribeCitySelections(getView())).subscribe();
    }

    private void subscribeCitySelections(WeatherView view) {
        view.getCitySelections()
            .flatMapCompletable(setCurrentCityInteractor::run)
            .compose(bindToLifecycle())
            .andThen(getWeatherViewModel())
            .doOnError(error -> {
                if (isViewAttached()) getView().showError(error);
            })
            .retryWhen(error -> view.getCitySelections())
            .subscribe(result -> {
                if (isViewAttached()) {
                    getView().setData(result);
                    showUpdateTime(result.weather().timestamp());
                }
            });
    }

    private Observable<WeatherViewModel> getWeatherViewModel() {
        return currentWeatherInteractor.run()
                                       .compose(bindToLifecycle())
                                       .flatMapMaybe(weather -> getFavoredCitiesInteractor.run()
                                                                                          .flatMapMaybe(cities -> getCurrentCityInteractor
                                                                                                  .run()
                                                                                                  .map(city -> WeatherViewModel
                                                                                                          .create(weather,
                                                                                                                  city,
                                                                                                                  WeatherViewModel.CityList
                                                                                                                          .create(cities)))));
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
