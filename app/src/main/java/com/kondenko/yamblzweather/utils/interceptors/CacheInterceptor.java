package com.kondenko.yamblzweather.utils.interceptors;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Response;


public class CacheInterceptor implements Interceptor {

    private int hours;

    public CacheInterceptor(int hours) {
        this.hours = hours;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        CacheControl cacheControl = new CacheControl.Builder()
                .maxAge(hours, TimeUnit.HOURS)
                .build();
        return response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build();
    }

}
