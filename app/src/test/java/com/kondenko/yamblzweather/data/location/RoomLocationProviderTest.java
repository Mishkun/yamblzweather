package com.kondenko.yamblzweather.data.location;

import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Location;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 11.08.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class RoomLocationProviderTest {

    private static final String ID = "ID";
    private static final String NAME = "IZHEVSK";
    private static final double longitude = 0.32;
    private static final double latitude = 0.78;
    private CityEntity cityEntity;
    private City city;
    private RoomLocationProvider roomLocationProvider;

    @Mock
    private CityDao cityDao;

    @Before
    public void setUp() throws Exception {
        roomLocationProvider = new RoomLocationProvider(cityDao);

        city = City.create(Location.builder().longitude(longitude).latitude(latitude).build(), NAME, ID);
        setupCityEntity();
    }

    private void setupCityEntity() {
        cityEntity = new CityEntity();
        cityEntity.setName(NAME);
        cityEntity.setLatitude(latitude);
        cityEntity.setLongitude(longitude);
        cityEntity.setPlace_id(ID);
    }

    @Test
    public void shouldGetCurrentCity() throws Exception {
        when(cityDao.getSelectedCity()).thenReturn(Maybe.just(cityEntity));

        TestObserver<City> cityTestObserver = roomLocationProvider.getCurrentCity().test();

        cityTestObserver.assertResult(city);
    }

    @Test
    public void shouldSetCurrentCityById() throws Exception {
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);

        TestObserver<Void> cityTestObserver = roomLocationProvider.setCurrentCity(city).test();

        cityTestObserver.assertComplete();
        verify(cityDao).deselectAll();
        verify(cityDao).setSelectedCity(stringCaptor.capture());
        assertEquals(city.id(), stringCaptor.getValue());
    }

    @Test
    public void shouldAddFavoriteCity() throws Exception {
        ArgumentCaptor<CityEntity> cityEntityCaptor = ArgumentCaptor.forClass(CityEntity.class);

        TestObserver<Void> cityTestObserver = roomLocationProvider.addFavoredCity(city).test();

        cityTestObserver.assertComplete();
        verify(cityDao).insertCity(cityEntityCaptor.capture());

        CityEntity testCityEntity = cityEntityCaptor.getValue();
        assertEquals(cityEntity, testCityEntity);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getFavoriteCities() throws Exception {
        List<CityEntity> cityEntities = new ArrayList<CityEntity>(){{add(cityEntity); add(cityEntity);}};
        List<City> cities = new ArrayList<City>(){{add(city); add(city);}};
        when(cityDao.getCities()).thenReturn(Single.just(cityEntities));

        TestObserver<List<City>> listTestObserver = roomLocationProvider.getFavoriteCities().test();

        listTestObserver.assertResult(cities);
    }

    @Test
    public void deleteFavoriteCity() throws Exception {
        ArgumentCaptor<CityEntity> cityEntityCaptor = ArgumentCaptor.forClass(CityEntity.class);

        TestObserver<Void> deletionTestObserver = roomLocationProvider.deleteFavoriteCity(city).test();

        deletionTestObserver.assertComplete();
        verify(cityDao).deleteCity(cityEntityCaptor.capture());
        assertEquals(cityEntity, cityEntityCaptor.getValue());
    }

}