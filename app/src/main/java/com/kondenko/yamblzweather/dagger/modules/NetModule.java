package com.kondenko.yamblzweather.dagger.modules;

import android.content.Context;

import com.kondenko.yamblzweather.utils.SettingsManager;
import com.kondenko.yamblzweather.utils.interceptors.ApiKeyInterceptor;
import com.kondenko.yamblzweather.utils.interceptors.CacheInterceptor;
import com.kondenko.yamblzweather.utils.interceptors.LoggingInterceptor;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    private String baseUrl;
    private String apiKey;

    public NetModule(String baseUrl, String apiKey) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    @Provides
    @Singleton
    public OkHttpClient provideHttpClient(Context context) {
        Cache cache = new Cache(new File(context.getCacheDir(), "http-cache"), 1024 * 1024);
        SettingsManager settingsManager = new SettingsManager(context);
        int refreshRate = settingsManager.getRefreshRateHr();
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new CacheInterceptor(refreshRate))
                .addInterceptor(new ApiKeyInterceptor(apiKey))
                .addInterceptor(new LoggingInterceptor())
                .build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
