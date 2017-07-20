package com.kondenko.yamblzweather.dagger.components;

import com.kondenko.yamblzweather.App;
import com.kondenko.yamblzweather.dagger.modules.BindersModule;
import com.kondenko.yamblzweather.dagger.modules.AppModule;
import com.kondenko.yamblzweather.dagger.modules.NetModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        BindersModule.class,
        AppModule.class,
        NetModule.class
        })
public interface AppComponent {

    void inject(App app);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(App application);

        Builder appModule(AppModule appModule);

        Builder netModule(NetModule appModule);

        AppComponent build();
    }

}


