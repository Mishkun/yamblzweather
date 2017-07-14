package com.kondenko.yamblzweather.ui.weather;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondenko.yamblzweather.R;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjection;

public class FragmentWeather extends Fragment implements WeatherView {

//    @Inject
//    public WeatherPresenter presenter;

    public FragmentWeather() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_weather, container, false);
        return root;
    }

}
