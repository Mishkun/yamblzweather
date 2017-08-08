package com.kondenko.yamblzweather.ui.weather;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Weather;

import java.util.List;

/**
 * Created by Mishkun on 06.08.2017.
 */
@AutoValue
abstract class WeatherViewModel implements Parcelable {
    public static WeatherViewModel create(Weather weather, City city, CityList cityList) {
        return new AutoValue_WeatherViewModel(weather, city, cityList);
    }

    abstract public Weather weather();

    public abstract City city();

    public abstract CityList cities();

    @AutoValue
    static abstract class CityList implements Parcelable {
        public abstract List<City> cities();
        public static CityList create(List<City> cities){
            return new AutoValue_WeatherViewModel_CityList(cities);
        }
    }
}
