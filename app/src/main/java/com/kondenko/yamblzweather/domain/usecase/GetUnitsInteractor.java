package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.domain.BaseInteractor;
import com.kondenko.yamblzweather.domain.entity.TempUnit;
import com.kondenko.yamblzweather.infrastructure.SettingsManager;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.Single;

import static com.kondenko.yamblzweather.Const.JOB;
import static com.kondenko.yamblzweather.Const.UI;

/**
 * Created by Mishkun on 10.08.2017.
 */

public class GetUnitsInteractor extends BaseInteractor {
    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;
    private final SettingsManager settingsManager;

    @Inject
    GetUnitsInteractor(@Named(JOB) Scheduler jobScheduler, @Named(UI) Scheduler uiScheduler, SettingsManager settingsManager) {
        this.jobScheduler = jobScheduler;
        this.uiScheduler = uiScheduler;
        this.settingsManager = settingsManager;
    }

    public Single<TempUnit> run() {
        return Single.fromCallable(settingsManager::getUnitKey)
                     .subscribeOn(jobScheduler)
                     .observeOn(uiScheduler);
    }
}
