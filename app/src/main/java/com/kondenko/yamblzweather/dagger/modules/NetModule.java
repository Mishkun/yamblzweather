package com.kondenko.yamblzweather.dagger.modules;

import android.content.Context;

import com.kondenko.yamblzweather.Const;
import com.kondenko.yamblzweather.utils.SettingsManager;
import com.kondenko.yamblzweather.utils.interceptors.ApiKeyInterceptor;
import com.kondenko.yamblzweather.utils.interceptors.CacheInterceptor;
import com.kondenko.yamblzweather.utils.interceptors.LoggingInterceptor;

import java.io.File;

import javax.inject.Inject;
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
    public Cache provideCache(Context context) {
        File cacheDir = new File(context.getCacheDir(), "http-cache");
        int cacheSize = 5 * 1024 * 1024;
        return new Cache(cacheDir, cacheSize);
    }

    @Provides
    @Singleton
    @Inject
    public CacheInterceptor provideCacheInterceptor(Context context, SettingsManager settingsManager) {
        Integer refreshRateSeconds = ((int) settingsManager.getRefreshRateSec());
        return new CacheInterceptor(context, refreshRateSeconds);
    }

    @Provides
    @Singleton
    public OkHttpClient provideHttpClient(Cache cache, CacheInterceptor cacheInterceptor) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new ApiKeyInterceptor(Const.PARAM_API_KEY, apiKey))
                .addInterceptor(cacheInterceptor)
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
