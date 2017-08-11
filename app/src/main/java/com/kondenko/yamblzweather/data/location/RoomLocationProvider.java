package com.kondenko.yamblzweather.data.location;

import android.util.Log;

import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.guards.LocationProvider;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Mishkun on 05.08.2017.
 */

public class RoomLocationProvider implements LocationProvider {
    private static final String TAG = RoomLocationProvider.class.getSimpleName();
    private final CityDao cityDao;

    public RoomLocationProvider(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    public Maybe<City> getCurrentCity() {
        return cityDao.getSelectedCity()
                      .map(CityMapper::dbToDomain);
    }

    @Override
    public Completable setCurrentCity(City city) {
        return Completable.fromAction(() -> {
            cityDao.deselectAll();
            cityDao.setSelectedCity(city.id());});
    }

    @Override
    public Completable addFavoredCity(City city) {
        return Completable.fromAction(() -> cityDao.insertCity(CityMapper.domainToDb(city)));
    }

    @Override
    public Single<List<City>> getFavoriteCities() {
        return cityDao.getCities().flatMap(cityEntities -> Observable.fromIterable(cityEntities)
                                                                     .map(CityMapper::dbToDomain)
                                                                     .toList());
    }

    @Override
    public Completable deleteFavoriteCity(City city) {
        return Completable.fromAction(() -> cityDao.deleteCity(CityMapper.domainToDb(city)));
    }
}
