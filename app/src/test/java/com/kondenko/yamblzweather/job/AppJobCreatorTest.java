package com.kondenko.yamblzweather.job;

import com.evernote.android.job.Job;
import com.kondenko.yamblzweather.ui.weather.WeatherInteractor;
import com.kondenko.yamblzweather.utils.SettingsManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

/**
 * Created by Mishkun on 25.07.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class AppJobCreatorTest {

    @Mock
    SettingsManager settingsManager;

    @Mock
    WeatherInteractor interactor;

    @Test
    public void shouldCreateWeatherJob() throws Exception {
        AppJobCreator appJobCreator = new AppJobCreator(interactor, settingsManager);
        Job updateJob = appJobCreator.create(UpdateWeatherJob.TAG);
        assertTrue(updateJob instanceof UpdateWeatherJob);
    }

}