package com.kondenko.yamblzweather.ui.weather;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Forecast;
import com.kondenko.yamblzweather.domain.entity.Weather;

import java.util.List;

/**
 * Created by Mishkun on 06.08.2017.
 */
@AutoValue
abstract class WeatherViewModel implements Parcelable {
    public static WeatherViewModel create(Weather weather, Forecast forecast, City city, CityList cityList) {
        return new AutoValue_WeatherViewModel(weather, forecast, city, cityList);
    }

    abstract public Weather weather();

    abstract Forecast forecast();

    public abstract City city();

    public abstract CityList cities();

    @AutoValue
    static abstract class CityList implements Parcelable {
        public static CityList create(List<City> cities) {
            return new AutoValue_WeatherViewModel_CityList(cities);
        }

        public abstract List<City> cities();
    }
}
