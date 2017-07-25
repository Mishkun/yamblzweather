package com.kondenko.yamblzweather.ui.weather;

import com.kondenko.yamblzweather.model.service.WeatherService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 24.07.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class WeatherInteractorTest {

    @Mock
    WeatherService weatherService;

    @Before
    public void setUp(){

    }

    @Test
    public void shouldGetWeatherAndUnwrapResponse() throws Exception {
    }

}