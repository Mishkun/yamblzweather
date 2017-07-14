package com.kondenko.yamblzweather.utils.interceptors;

import android.util.Log;

import com.kondenko.yamblzweather.utils.L;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoggingInterceptor implements Interceptor {

    private static final String TAG = "HTTP-Log";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        L.i(TAG, request.toString());
        L.i(TAG, response.toString());
        return response;
    }

}