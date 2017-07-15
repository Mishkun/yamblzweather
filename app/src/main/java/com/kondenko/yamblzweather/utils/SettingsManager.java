package com.kondenko.yamblzweather.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.kondenko.yamblzweather.R;

public class SettingsManager {

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

}
