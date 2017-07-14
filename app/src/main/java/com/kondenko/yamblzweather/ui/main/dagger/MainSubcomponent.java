package com.kondenko.yamblzweather.ui.main.dagger;

import com.kondenko.yamblzweather.ui.main.MainActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {MainModule.class})
public interface MainSubcomponent extends AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    public abstract class Builder extends AndroidInjector.Builder<MainActivity> {
    }

}
