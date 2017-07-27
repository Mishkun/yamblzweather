package com.kondenko.yamblzweather.ui.citysuggest;

import android.content.Context;
import android.content.SharedPreferences;

import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.model.entity.City;
import com.kondenko.yamblzweather.model.entity.Coord;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;

/**
 * Created by Mishkun on 27.07.2017.
 */

public class SharedPrefsLoactionStore implements LocationStore {
    private final SharedPreferences sharedPreferences;
    private final String LONGITUDE_KEY;
    private final String LATITUDE_KEY;
    private final float DEFAULT_LONGITUDE = 55.7558f;
    private final float DEFAULT_LATITUDE = 37.6173f;
    private final String DEFAULT_CITY_NAME;
    private final String CITY_NAME_KEY;


    public SharedPrefsLoactionStore(Context context) {
        this.sharedPreferences = context.getSharedPreferences(context.getPackageName() + "_preferences", Context.MODE_PRIVATE);
        this.LATITUDE_KEY = context.getString(R.string.pref_key_latitude);
        this.LONGITUDE_KEY = context.getString(R.string.pref_key_longitude);
        this.DEFAULT_CITY_NAME = context.getString(R.string.default_city);
        this.CITY_NAME_KEY = context.getString(R.string.pref_key_city);
    }

    @Override
    public Single<City> getCurrentCity() {
        return Single.zip(Single.fromCallable(() -> sharedPreferences.getFloat(LATITUDE_KEY, DEFAULT_LATITUDE)),
                          Single.fromCallable(() -> sharedPreferences.getFloat(LONGITUDE_KEY, DEFAULT_LONGITUDE)),
                          (BiFunction<Float, Float, Coord>) Coord::new)
                     .zipWith(Single.fromCallable(() -> sharedPreferences.getString(CITY_NAME_KEY, DEFAULT_CITY_NAME)),
                              City::new);
    }

    @Override
    public Completable setCurrentCity(City city) {
        return Completable.fromAction(() -> sharedPreferences.edit()
                                                             .putFloat(LATITUDE_KEY, (float) city.getCoordinates().getLat())
                                                             .putFloat(LONGITUDE_KEY, (float) city.getCoordinates().getLon())
                                                             .putString(CITY_NAME_KEY, city.getCity()));
    }
}
