package com.kondenko.yamblzweather.ui.weather;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondenko.yamblzweather.R;

public class FragmentWeather extends Fragment {

    public FragmentWeather() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_weather, container, false);
        return root;
    }

}
