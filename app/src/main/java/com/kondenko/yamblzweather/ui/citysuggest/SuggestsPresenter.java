package com.kondenko.yamblzweather.ui.citysuggest;

import com.kondenko.yamblzweather.ui.BasePresenter;

import javax.inject.Inject;

/**
 * Created by Mishkun on 28.07.2017.
 */

public class SuggestsPresenter extends BasePresenter<SuggestsView> {
    private final CitySuggestInteractor citySuggestInteractor;
    private final FetchCityCoords fetchCityCoords;

    @Inject
    public SuggestsPresenter(CitySuggestInteractor interactor, FetchCityCoords fetchCityCoords) {
        this.fetchCityCoords = fetchCityCoords;
        this.citySuggestInteractor = interactor;
    }

    @Override
    public void attachView(SuggestsView view) {
        super.attachView(view);
        view.getCityNamesStream()
            .compose(bindToLifecycle())
            .doOnNext((ignore) -> {
                if (isViewAttached()) getView().showLoading(true);
            })
            .flatMapSingle(citySuggestInteractor::getCitySuggests)
            .doOnEach((ignore) -> {
                if (isViewAttached()) getView().showLoading(false);
            })
            .doOnError(error -> {
                if (isViewAttached()) getView().showError(error);
            })
            .retryWhen((error) -> view.getCityNamesStream())
            .subscribe((result) -> {
                           if (isViewAttached()) {
                               getView().setData(result);
                           }
                       }
            );
        view.getClicks()
            .compose(bindToLifecycle())
            .flatMapCompletable(prediction -> fetchCityCoords.getCityCoordinatesAndWrite(prediction)
                                                             .doOnComplete(() -> {
                                                                 if (isViewAttached()) {
                                                                     getView().finishScreen();
                                                                 }
                                                             })
                                                             .doOnError(error -> {
                                                                 if (isViewAttached()) getView().showError(
                                                                         error);
                                                             })
                                                             .onErrorComplete())
            .subscribe();
    }

    @Override
    public void detachView() {
        super.detachView();
    }
}
