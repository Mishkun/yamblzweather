package com.kondenko.yamblzweather.ui.citysuggest.dagger;

import com.kondenko.yamblzweather.data.suggest.CitiesSuggestService;
import com.kondenko.yamblzweather.ui.citysuggest.SuggestsActivity;
import com.kondenko.yamblzweather.ui.citysuggest.SuggestsView;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

import static com.kondenko.yamblzweather.di.modules.NetModule.GOOGLE_SUGGESTS_API;

/**
 * Created by Mishkun on 28.07.2017.
 */
@Module
public class SuggestsModule {
    @Provides
    public SuggestsView provideView(SuggestsActivity activity) {
        return activity;
    }

    @Provides
    CitiesSuggestService provideCitiesSuggestService(@Named(GOOGLE_SUGGESTS_API) Retrofit retrofit) {
        return retrofit.create(CitiesSuggestService.class);
    }
}
