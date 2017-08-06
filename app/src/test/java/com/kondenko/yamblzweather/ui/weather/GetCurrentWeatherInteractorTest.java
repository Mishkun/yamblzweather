package com.kondenko.yamblzweather.ui.weather;

import com.kondenko.yamblzweather.data.weather.WeatherModel;
import com.kondenko.yamblzweather.data.weather.WeatherService;
import com.kondenko.yamblzweather.domain.guards.LocationProvider;
import com.kondenko.yamblzweather.infrastructure.SettingsManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.verify;

/**
 * Created by Mishkun on 24.07.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetCurrentWeatherInteractorTest {

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
    LocationProvider locationProvider;

    private TestScheduler testScheduler;

    @Before
    public void setUp() {
        testScheduler = new TestScheduler();

    }

    @Test
    public void shouldGetWeatherAndAdjustTimestamp() throws Exception {




    }

    @Test
    public void shouldAlsoGetWeatherAndAdjustTimestampButFromAnotherEndpoint() throws Exception {

    }

}