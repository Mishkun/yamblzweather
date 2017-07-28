package com.kondenko.yamblzweather.ui.weather;

import com.kondenko.yamblzweather.Const;
import com.kondenko.yamblzweather.model.entity.City;
import com.kondenko.yamblzweather.model.entity.Coord;
import com.kondenko.yamblzweather.model.entity.WeatherModel;
import com.kondenko.yamblzweather.model.service.WeatherService;
import com.kondenko.yamblzweather.ui.citysuggest.LocationStore;
import com.kondenko.yamblzweather.utils.SettingsManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 24.07.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class WeatherInteractorTest {

    private static final String ANYTHING = "ANYTHING";
    private final float DEFAULT_LONGITUDE = 55.7558f;
    private final float DEFAULT_LATITUDE = 37.6173f;
    @Mock
    WeatherService weatherService;

    @Mock
    WeatherModel weatherModel;

    @Mock
    SettingsManager settingsManager;

    @Mock
    LocationStore locationStore;

    private TestScheduler testScheduler;

    @Before
    public void setUp() {
        testScheduler = new TestScheduler();
        when(locationStore.getCurrentCity()).thenReturn(Single.just(new City(new Coord(DEFAULT_LATITUDE, DEFAULT_LONGITUDE), ANYTHING)));
    }

    @Test
    public void shouldGetWeatherAndAdjustTimestamp() throws Exception {
        when(weatherService.getWeather(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)).thenReturn(Single.just(weatherModel));
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        WeatherInteractor interactor = new WeatherInteractor(testScheduler, testScheduler, weatherService, settingsManager, locationStore);

        TestObserver<WeatherModel> weatherModelTestObserver = interactor.getWeather(Const.KEY_UNIT_TEMP_DEFAULT).test();
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        verify(weatherService).getWeather(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
        verify(weatherService, never()).getWeather(anyFloat(), anyFloat(), anyString());

        verify(settingsManager).setLatestUpdate(longArgumentCaptor.capture());
        verify(weatherModel).setTimestamp(longArgumentCaptor.getValue());

        weatherModelTestObserver.assertResult(weatherModel);
        weatherModelTestObserver.assertComplete();
    }

    @Test
    public void shouldAlsoGetWeatherAndAdjustTimestampButFromAnotherEndpoint() throws Exception {
        when(weatherService.getWeather(DEFAULT_LATITUDE, DEFAULT_LONGITUDE, ANYTHING)).thenReturn(Single.just(weatherModel));
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        WeatherInteractor interactor = new WeatherInteractor(testScheduler, testScheduler, weatherService, settingsManager, locationStore);

        TestObserver<WeatherModel> weatherModelTestObserver = interactor.getWeather(ANYTHING).test();
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        verify(weatherService).getWeather(DEFAULT_LATITUDE, DEFAULT_LONGITUDE, ANYTHING);
        verify(weatherService, never()).getWeather(anyFloat(), anyFloat());


        verify(settingsManager).setLatestUpdate(longArgumentCaptor.capture());
        verify(weatherModel).setTimestamp(longArgumentCaptor.getValue());

        weatherModelTestObserver.assertResult(weatherModel);
        weatherModelTestObserver.assertComplete();
    }

}