package com.kondenko.yamblzweather.ui.citysuggest;

import com.kondenko.yamblzweather.domain.usecase.GetCitySuggestsInteractor;
import com.kondenko.yamblzweather.domain.usecase.FetchCityCoords;
import com.kondenko.yamblzweather.ui.BasePresenter;

import javax.inject.Inject;

/**
 * Created by Mishkun on 28.07.2017.
 */

public class SuggestsPresenter extends BasePresenter<SuggestsView> {
    private final GetCitySuggestsInteractor getCitySuggestsInteractor;
    private final FetchCityCoords fetchCityCoords;

    @Inject
    public SuggestsPresenter(GetCitySuggestsInteractor interactor, FetchCityCoords fetchCityCoords) {
        this.fetchCityCoords = fetchCityCoords;
        this.getCitySuggestsInteractor = interactor;
    }

    @Override
    public void attachView(SuggestsView view) {
        super.attachView(view);
        view.getCityNamesStream()
            .compose(bindToLifecycle())
            .doOnNext((ignore) -> {
                if (isViewAttached()) getView().showLoading(true);
            })
            .flatMapSingle(getCitySuggestsInteractor::run)
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
