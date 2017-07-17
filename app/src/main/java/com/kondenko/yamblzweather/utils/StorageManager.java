package com.kondenko.yamblzweather.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class StorageManager {

    private static final String KEY_PREF_CURRENT_WEATHER_JSON = "current_weather";

    private Context context;

    private SharedPreferences sharedPreferences;

    public StorageManager(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveCurrentWeather(String json) {
        sharedPreferences.edit().putString(KEY_PREF_CURRENT_WEATHER_JSON, json).apply();
    }

    public String getCachedCurrentWeather() {
        return sharedPreferences.getString(KEY_PREF_CURRENT_WEATHER_JSON, "");
    }

}
