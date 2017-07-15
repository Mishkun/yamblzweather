package com.kondenko.yamblzweather.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.kondenko.yamblzweather.Const;
import com.kondenko.yamblzweather.R;

import dagger.multibindings.StringKey;

public class SettingsManager {

    public static final String KEY_SELECTED_CITY = "selected_city";

    private Context context;
    private SharedPreferences preferences;

    public SettingsManager(Context context) {
        this.context = context;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getSelectedUnitKey() {
        String unitKey = context.getString(R.string.pref_key_temp_unit);
        String defaultUnit = context.getString(R.string.pref_key_unit_kelvin);
        return preferences.getString(unitKey, defaultUnit);
    }

    public String getSelectedUnitValue() {
        String selectedUnitKey = getSelectedUnitKey();
        if (selectedUnitKey.equals(context.getString(R.string.pref_key_unit_fahrenheit)))
            return context.getString(R.string.pref_value_unit_fahrenheit);
        if (selectedUnitKey.equals(context.getString(R.string.pref_key_unit_celsius)))
            return context.getString(R.string.pref_value_unit_celsius);
        if (selectedUnitKey.equals(context.getString(R.string.pref_key_unit_kelvin)))
            return context.getString(R.string.pref_value_unit_kelvin);
        throw new IllegalArgumentException("Wrong temperature unit");
    }

    public int getRefreshRate() {
        String rateKey = context.getString(R.string.pref_key_refresh_rate);
        int defaultRate = Const.PREF_REFRESH_RATE_DEFAULT_HOURS;
        return preferences.getInt(rateKey, defaultRate);
    }

    public void setSelectedCity(String cityId) {
        preferences.edit().putString(KEY_SELECTED_CITY, cityId).apply();
    }

    public String getSelectedCity() {
        return preferences.getString(KEY_SELECTED_CITY, null);
    }

}
