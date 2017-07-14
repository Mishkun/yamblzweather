package com.kondenko.yamblzweather.utils;


import android.support.compat.BuildConfig;
import android.util.Log;


public class L {

    public static void w(String tag, Throwable t) {
        logIfDebug(() -> Log.w(tag, t.getMessage(), t));
    }

    public static void i(String tag, String msg) {
        logIfDebug(() -> Log.i(tag, msg));
    }


    private static void logIfDebug(Function function) {
//        if (BuildConfig.DEBUG) {
            function.run();
//        }
    }

}
