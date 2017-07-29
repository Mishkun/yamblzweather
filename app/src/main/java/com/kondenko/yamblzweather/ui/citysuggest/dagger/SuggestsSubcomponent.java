package com.kondenko.yamblzweather.ui.citysuggest.dagger;

import com.kondenko.yamblzweather.ui.citysuggest.SuggestsActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by Mishkun on 28.07.2017.
 */
@Subcomponent(modules = SuggestsModule.class)
public interface SuggestsSubcomponent extends AndroidInjector<SuggestsActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<SuggestsActivity> {

    }
}
