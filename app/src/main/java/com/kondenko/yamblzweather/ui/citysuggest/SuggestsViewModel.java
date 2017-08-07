package com.kondenko.yamblzweather.ui.citysuggest;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Location;
import com.kondenko.yamblzweather.domain.entity.Prediction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mishkun on 05.08.2017.
 */
@AutoValue
abstract class SuggestsViewModel implements Parcelable {
    abstract List<Prediction> predictions();
    abstract List<City> cities();
    abstract City selectedCity();

    @NonNull
    static SuggestsViewModel createWithPredictions(@NonNull List<Prediction> predictions) {
        return new AutoValue_SuggestsViewModel(predictions, new ArrayList<>(), defaultCity());
    }

    private static City defaultCity() {
        return City.create(Location.builder().longitude(0).latitude(0).build(), "ID", "name");
    }

    @NonNull
    static SuggestsViewModel createWithCities(@NonNull List<City> cities, @NonNull City city){return new AutoValue_SuggestsViewModel(new ArrayList<>(), cities, city);}
}
