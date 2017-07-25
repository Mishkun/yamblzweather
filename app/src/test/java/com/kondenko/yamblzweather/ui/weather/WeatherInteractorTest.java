package com.kondenko.yamblzweather.ui.weather;

import com.kondenko.yamblzweather.Const;
import com.kondenko.yamblzweather.model.entity.WeatherModel;
import com.kondenko.yamblzweather.model.service.WeatherService;
import com.kondenko.yamblzweather.utils.SettingsManager;

import org.hamcrest.core.AnyOf;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 24.07.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class WeatherInteractorTest {

    public static final String ANYTHING = "ANYTHING";
    @Mock
    WeatherService weatherService;

    @Mock
    WeatherModel weatherModel;

    @Mock
    SettingsManager settingsManager;

    TestScheduler  testScheduler;

    @Before
    public void setUp() {
        testScheduler = new TestScheduler();
    }

    @Test
    public void shouldGetWeatherAndAdjustTimestamp() throws Exception {
        when(weatherService.getWeather(Const.ID_MOSCOW)).thenReturn(Single.just(weatherModel));
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        WeatherInteractor interactor = new WeatherInteractor(testScheduler, testScheduler, weatherService, settingsManager);

        TestObserver<WeatherModel> weatherModelTestObserver = interactor.getWeather(Const.ID_MOSCOW, Const.KEY_UNIT_TEMP_DEFAULT).test();
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        verify(weatherService).getWeather(Const.ID_MOSCOW);
        verify(weatherService, never()).getWeather(anyString(), anyString());

        verify(settingsManager).setLatestUpdate(longArgumentCaptor.capture());
        verify(weatherModel).setTimestamp(longArgumentCaptor.getValue());

        weatherModelTestObserver.assertResult(weatherModel);
        weatherModelTestObserver.assertComplete();
    }

    @Test
    public void shouldAlsoGetWeatherAndAdjustTimestampButFromAnotherEndpoint() throws Exception {
        when(weatherService.getWeather(Const.ID_MOSCOW, ANYTHING)).thenReturn(Single.just(weatherModel));
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        WeatherInteractor interactor = new WeatherInteractor(testScheduler, testScheduler, weatherService, settingsManager);

        TestObserver<WeatherModel> weatherModelTestObserver = interactor.getWeather(Const.ID_MOSCOW, ANYTHING).test();
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        verify(weatherService).getWeather(Const.ID_MOSCOW, ANYTHING);
        verify(weatherService, never()).getWeather(anyString());


        verify(settingsManager).setLatestUpdate(longArgumentCaptor.capture());
        verify(weatherModel).setTimestamp(longArgumentCaptor.getValue());

        weatherModelTestObserver.assertResult(weatherModel);
        weatherModelTestObserver.assertComplete();
    }

}