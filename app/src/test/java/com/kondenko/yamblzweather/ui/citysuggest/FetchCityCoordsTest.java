package com.kondenko.yamblzweather.ui.citysuggest;

import com.kondenko.yamblzweather.model.entity.City;
import com.kondenko.yamblzweather.model.entity.CitySearchResult;
import com.kondenko.yamblzweather.model.entity.Coord;
import com.kondenko.yamblzweather.model.entity.Prediction;
import com.kondenko.yamblzweather.model.service.CitiesSuggestService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 29.07.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class FetchCityCoordsTest {

    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String PLACE_ID = "PLACE_ID";
    private static final double LATITUDE = 0.0;
    private static final double LONGITUDE = 1.0;
    @Mock
    private LocationStore locationStore;
    @Mock
    private CitiesSuggestService citiesSuggestService;


    private TestScheduler testScheduler = new TestScheduler();
    private Prediction prediction;
    private CitySearchResult citySearchResult;
    private Coord coordinates;

    @Before
    public void setUp() throws Exception {
        prediction = new Prediction(DESCRIPTION, PLACE_ID);
        citySearchResult = mock(CitySearchResult.class, RETURNS_DEEP_STUBS);
        coordinates = new Coord();
        coordinates.setLat(LATITUDE);
        coordinates.setLon(LONGITUDE);
        when(citySearchResult.getResult().getGeometry().getCoordinates()).thenReturn(coordinates);
    }

    @Test
    public void shouldGetCityCoordinatesAndWrite() throws Exception {
        City testCity = new City(coordinates, DESCRIPTION);
        when(citiesSuggestService.getCityCoordinatesById(PLACE_ID)).thenReturn(Single.just(citySearchResult));
        when(locationStore.setCurrentCity(any())).thenReturn(Completable.complete());
        ArgumentCaptor<City> cityArgumentCaptor = ArgumentCaptor.forClass(City.class);

        FetchCityCoords fetchCityCoords = new FetchCityCoords(testScheduler, testScheduler, citiesSuggestService, locationStore);
        TestObserver<Void> test = fetchCityCoords.getCityCoordinatesAndWrite(prediction).test();
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);
        test.assertResult();

        verify(citiesSuggestService).getCityCoordinatesById(PLACE_ID);
        verifyNoMoreInteractions(citiesSuggestService);
        verify(locationStore).setCurrentCity(cityArgumentCaptor.capture());
        verifyNoMoreInteractions(locationStore);

        City cityResult = cityArgumentCaptor.getValue();
        assertTrue(cityResult.equals(testCity));
    }

}