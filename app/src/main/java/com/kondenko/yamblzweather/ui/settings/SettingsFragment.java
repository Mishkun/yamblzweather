package com.kondenko.yamblzweather.ui.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.kondenko.yamblzweather.R;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.app_preferences);
    }

}
