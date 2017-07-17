package com.kondenko.yamblzweather.utils.interceptors;

import android.content.Context;

import com.kondenko.yamblzweather.Const;
import com.kondenko.yamblzweather.utils.Utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class CacheInterceptor implements Interceptor {

    private Context context;
    private int maxStaleSec;

    @Inject
    public CacheInterceptor(Context context, Integer updateRateSec) {
        this.context = context;
        this.maxStaleSec = updateRateSec;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        CacheControl.Builder cacheControl = new CacheControl.Builder();
        if (Utils.isOnline(context)) {
            cacheControl.maxAge(Const.DEFAULT_CACHING_TIME_SECONDS, TimeUnit.SECONDS);
        } else {
            cacheControl.onlyIfCached().maxStale(maxStaleSec, TimeUnit.SECONDS);
        }
        request = request.newBuilder().cacheControl(cacheControl.build()).build();
        return chain.proceed(request);
    }
}

