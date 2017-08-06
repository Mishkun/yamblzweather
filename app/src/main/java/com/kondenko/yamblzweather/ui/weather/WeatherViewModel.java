package com.kondenko.yamblzweather.ui.weather;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Weather;

/**
 * Created by Mishkun on 06.08.2017.
 */
@AutoValue
abstract class WeatherViewModel implements Parcelable{
    abstract public Weather weather();

    public static WeatherViewModel create(Weather weather, City city){
        return new AutoValue_WeatherViewModel(weather, city);
    }

    public abstract City city();
}
