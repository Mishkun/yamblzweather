package com.kondenko.yamblzweather.utils;

import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.model.entity.Weather;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 22.07.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class WeatherUtilsTest {

    @Mock
    Weather weather;

    @Test
    public void mustReturnDefaultIconStringResource() throws Exception {
        when(weather.getId()).thenReturn(0);
        assertEquals(R.string.wi_na, WeatherUtils.getIconStringResource(weather));

        when(weather.getId()).thenReturn(1001);
        assertEquals(R.string.wi_na, WeatherUtils.getIconStringResource(weather));
    }

    @Test
    public void mustReturnThunderstormIconStringResource() throws Exception {
        when(weather.getId()).thenReturn(205);
        assertEquals(R.string.wi_thunderstorm, WeatherUtils.getIconStringResource(weather));

        when(weather.getId()).thenReturn(200);
        assertEquals(R.string.wi_thunderstorm, WeatherUtils.getIconStringResource(weather));

        when(weather.getId()).thenReturn(232);
        assertEquals(R.string.wi_thunderstorm, WeatherUtils.getIconStringResource(weather));
    }

    @Test
    public void mustReturnShowersIconStringResource() throws Exception {
        when(weather.getId()).thenReturn(310);
        assertEquals(R.string.wi_showers, WeatherUtils.getIconStringResource(weather));

        when(weather.getId()).thenReturn(300);
        assertEquals(R.string.wi_showers, WeatherUtils.getIconStringResource(weather));

        when(weather.getId()).thenReturn(321);
        assertEquals(R.string.wi_showers, WeatherUtils.getIconStringResource(weather));
    }

    @Test
    public void mustReturnRainIconStringResource() throws Exception {
        when(weather.getId()).thenReturn(501);
        assertEquals(R.string.wi_rain, WeatherUtils.getIconStringResource(weather));

        when(weather.getId()).thenReturn(500);
        assertEquals(R.string.wi_rain, WeatherUtils.getIconStringResource(weather));

        when(weather.getId()).thenReturn(531);
        assertEquals(R.string.wi_rain, WeatherUtils.getIconStringResource(weather));
    }

    @Test
    public void mustReturnSnowIconStringResource() throws Exception {
        when(weather.getId()).thenReturn(600);
        assertEquals(R.string.wi_snow, WeatherUtils.getIconStringResource(weather));

        when(weather.getId()).thenReturn(622);
        assertEquals(R.string.wi_snow, WeatherUtils.getIconStringResource(weather));

        when(weather.getId()).thenReturn(615);
        assertEquals(R.string.wi_snow, WeatherUtils.getIconStringResource(weather));
    }

    @Test
    public void mustReturnFogIconStringResource() throws Exception {
        when(weather.getId()).thenReturn(700);
        assertEquals(R.string.wi_fog, WeatherUtils.getIconStringResource(weather));

        when(weather.getId()).thenReturn(756);
        assertEquals(R.string.wi_fog, WeatherUtils.getIconStringResource(weather));

        when(weather.getId()).thenReturn(781);
        assertEquals(R.string.wi_fog, WeatherUtils.getIconStringResource(weather));
    }

    @Test
    public void mustReturnSunnyIconStringResource() throws Exception {
        when(weather.getId()).thenReturn(800);
        assertEquals(R.string.wi_day_sunny, WeatherUtils.getIconStringResource(weather));
    }

    @Test
    public void mustReturnCloudIconStringResource() throws Exception {
        when(weather.getId()).thenReturn(801);
        assertEquals(R.string.wi_cloud, WeatherUtils.getIconStringResource(weather));

        when(weather.getId()).thenReturn(802);
        assertEquals(R.string.wi_cloud, WeatherUtils.getIconStringResource(weather));

        when(weather.getId()).thenReturn(803);
        assertEquals(R.string.wi_cloud, WeatherUtils.getIconStringResource(weather));

        when(weather.getId()).thenReturn(804);
        assertEquals(R.string.wi_cloud, WeatherUtils.getIconStringResource(weather));
    }
}