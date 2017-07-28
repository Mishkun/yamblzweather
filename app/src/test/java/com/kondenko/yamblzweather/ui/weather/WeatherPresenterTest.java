package com.kondenko.yamblzweather.ui.weather;

import android.content.Context;

import com.kondenko.yamblzweather.Const;
import com.kondenko.yamblzweather.model.entity.WeatherModel;
import com.kondenko.yamblzweather.utils.SettingsManager;
import com.kondenko.yamblzweather.utils.Utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Single;
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
    WeatherInteractor weatherInteractor;
    @Mock
    SettingsManager settingsManager;
    @Mock
    JobsRepository weatherJobScheduler;
    @Mock
    Context context;
    @Mock
    WeatherModel weatherModel;
    @Mock
    com.kondenko.yamblzweather.model.entity.Main main;

    @Before
    public void setUp() {
        when(settingsManager.getCity()).thenReturn(Const.ID_MOSCOW);
        when(settingsManager.getUnitKey()).thenReturn(DEFAULT);
        when(settingsManager.getUnitValue()).thenReturn(F);
        when(settingsManager.getRefreshRateHr()).thenReturn(REFRESH_RATE);
        when(weatherModel.getTimestamp()).thenReturn(TIMESTAMP);

        when(weatherModel.getMain()).thenReturn(main);
    }

    @Test
    public void shouldLoadData() throws Exception {
        when(weatherInteractor.getWeather(DEFAULT)).thenReturn(Single.just(weatherModel));
        WeatherPresenter presenter = new WeatherPresenter(weatherInteractor, weatherJobScheduler, settingsManager);
        InOrder loadingInOrder = inOrder(weatherView, weatherInteractor);
        presenter.attachView(weatherView);
        presenter.loadData();
        verify(settingsManager).getRefreshRateHr();
        verify(weatherJobScheduler).scheduleUpdateJob(REFRESH_RATE);
        verifyNoMoreInteractions(weatherJobScheduler);

        loadingInOrder.verify(weatherView).showLoading(true);

        loadingInOrder.verify(weatherInteractor).getWeather(DEFAULT);
        verifyNoMoreInteractions(weatherInteractor);

        loadingInOrder.verify(weatherView).showLoading(false);

        verify(settingsManager).getUnitKey();

        verify(weatherView).setData(weatherModel);
        verify(weatherView).showLatestUpdate(Utils.millisTo24time(TIMESTAMP));
        verify(settingsManager).getUnitValue();
        verify(main).setTempUnitKey(F);

        verifyNoMoreInteractions(settingsManager);
        verifyNoMoreInteractions(weatherView);
    }

    @Test
    public void shouldShowError() {
        HttpException nothing = new HttpException(Response.error(404, ResponseBody.create(null, "NOTHING")));
        when(weatherInteractor.getWeather(DEFAULT)).thenReturn(Single.error(nothing));
        WeatherPresenter weatherPresenter = new WeatherPresenter(weatherInteractor, weatherJobScheduler, settingsManager);
        InOrder loadingInOrder = inOrder(weatherView, weatherInteractor);

        weatherPresenter.attachView(weatherView);
        weatherPresenter.loadData();
        verify(settingsManager).getRefreshRateHr();
        verify(weatherJobScheduler).scheduleUpdateJob(REFRESH_RATE);
        verifyNoMoreInteractions(weatherJobScheduler);

        loadingInOrder.verify(weatherView).showLoading(true);

        loadingInOrder.verify(weatherInteractor).getWeather(DEFAULT);
        verifyNoMoreInteractions(weatherInteractor);

        loadingInOrder.verify(weatherView).showLoading(false);

        verify(settingsManager).getUnitKey();
        verifyNoMoreInteractions(settingsManager);

        verify(weatherView).showError(nothing);
        verifyNoMoreInteractions(weatherView);
    }


    @Test
    public void shouldDetachViewAndNotShowData() {
        when(weatherInteractor.getWeather(DEFAULT)).thenReturn(Single.just(weatherModel));
        WeatherPresenter succcessPresenter = new WeatherPresenter(weatherInteractor, weatherJobScheduler, settingsManager);
        succcessPresenter.attachView(weatherView);
        succcessPresenter.detachView();
        succcessPresenter.loadData();

        verifyZeroInteractions(weatherView);
    }

    @Test
    public void shouldDetachViewAndNotShowError() {
        HttpException nothing = new HttpException(Response.error(404, ResponseBody.create(null, "NOTHING")));
        when(weatherInteractor.getWeather(DEFAULT)).thenReturn(Single.error(nothing));
        WeatherPresenter errorPresenter = new WeatherPresenter(weatherInteractor, weatherJobScheduler, settingsManager);
        errorPresenter.attachView(weatherView);
        errorPresenter.detachView();
        errorPresenter.loadData();

        verifyZeroInteractions(weatherView);
    }
}