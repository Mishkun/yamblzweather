package com.kondenko.yamblzweather.dagger;

import android.app.Activity;

import com.kondenko.yamblzweather.ui.main.MainActivity;
import com.kondenko.yamblzweather.ui.main.dagger.MainSubcomponent;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module
public abstract class BindersModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    public abstract AndroidInjector.Factory<? extends Activity>
    bindMainActivityInjectorFactory(MainSubcomponent.Builder builder);

}
