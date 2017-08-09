package com.kondenko.yamblzweather.ui.weather;

import android.content.Context;

import com.kondenko.yamblzweather.Const;
import com.kondenko.yamblzweather.data.weather.WeatherModel;
import com.kondenko.yamblzweather.domain.guards.JobsScheduler;
import com.kondenko.yamblzweather.domain.usecase.GetCurrentCityInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetCurrentWeatherInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetFavoredCitiesInteractor;
import com.kondenko.yamblzweather.domain.usecase.UpdateWeatherInteractor;
import com.kondenko.yamblzweather.infrastructure.SettingsManager;
import com.kondenko.yamblzweather.utils.Utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 22.07.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class WeatherPresenterTest {

    private static final String DEFAULT = "default";
    private static final String F = "f";
    private static final int REFRESH_RATE = 10;
    private static final long TIMESTAMP = 69;

    @Mock
    WeatherView weatherView;
    @Mock
    GetCurrentWeatherInteractor getCurrentWeatherInteractor;
    @Mock
    SettingsManager settingsManager;
    @Mock
    UpdateWeatherInteractor updateWeatherInteractor;
    @Mock
    GetCurrentCityInteractor getCurrentCityInteractor;
    @Mock
    JobsScheduler weatherJobScheduler;
    @Mock
    Context context;
    @Mock
    WeatherModel weatherModel;
    @Mock
    WeatherModel.Main main;
    @Mock
    GetFavoredCitiesInteractor getFavoredCities;

    @Before
    public void setUp() {
        when(settingsManager.getCity()).thenReturn(Const.ID_MOSCOW);
        when(settingsManager.getUnitKey()).thenReturn(DEFAULT);
        when(settingsManager.getUnitValue()).thenReturn(F);
        when(settingsManager.getRefreshRateHr()).thenReturn(REFRESH_RATE);

        when(weatherModel.getMain()).thenReturn(main);
    }

    @Test
    public void shouldLoadData() throws Exception {

        InOrder loadingInOrder = inOrder(weatherView, getCurrentWeatherInteractor);

        verify(settingsManager).getRefreshRateHr();
        verify(weatherJobScheduler).scheduleUpdateJob(REFRESH_RATE);
        verifyNoMoreInteractions(weatherJobScheduler);

        loadingInOrder.verify(weatherView).showLoading(true);

        verifyNoMoreInteractions(getCurrentWeatherInteractor);

        loadingInOrder.verify(weatherView).showLoading(false);

        verify(settingsManager).getUnitKey();

        verify(weatherView).showLatestUpdate(Utils.millisTo24time(TIMESTAMP));
        verify(settingsManager).getUnitValue();

        verifyNoMoreInteractions(settingsManager);
        verifyNoMoreInteractions(weatherView);
    }

    @Test
    public void shouldShowError() {
        HttpException nothing = new HttpException(Response.error(404, ResponseBody.create(null, "NOTHING")));
        when(getCurrentWeatherInteractor.run()).thenReturn(Observable.error(nothing));

        InOrder loadingInOrder = inOrder(weatherView, getCurrentWeatherInteractor);

        verify(settingsManager).getRefreshRateHr();
        verify(weatherJobScheduler).scheduleUpdateJob(REFRESH_RATE);
        verifyNoMoreInteractions(weatherJobScheduler);

        loadingInOrder.verify(weatherView).showLoading(true);

        verifyNoMoreInteractions(getCurrentWeatherInteractor);

        loadingInOrder.verify(weatherView).showLoading(false);

        verify(settingsManager).getUnitKey();
        verifyNoMoreInteractions(settingsManager);

        verify(weatherView).showError(nothing);
        verifyNoMoreInteractions(weatherView);
    }


    @Test
    public void shouldDetachViewAndNotShowData() {
        verifyZeroInteractions(weatherView);
    }

    @Test
    public void shouldDetachViewAndNotShowError() {
        verifyZeroInteractions(weatherView);
    }
}