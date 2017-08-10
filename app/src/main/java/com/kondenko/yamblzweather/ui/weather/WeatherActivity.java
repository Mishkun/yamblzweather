package com.kondenko.yamblzweather.ui.weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pwittchen.weathericonview.WeatherIconView;
import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Location;
import com.kondenko.yamblzweather.domain.entity.TempUnit;
import com.kondenko.yamblzweather.domain.entity.Temperature;
import com.kondenko.yamblzweather.domain.entity.Weather;
import com.kondenko.yamblzweather.domain.entity.WeatherConditions;
import com.kondenko.yamblzweather.ui.BaseMvpActivity;
import com.kondenko.yamblzweather.ui.about.AboutActivity;
import com.kondenko.yamblzweather.ui.citysuggest.SuggestsActivity;
import com.kondenko.yamblzweather.ui.onboarding.OnboardingActivity;
import com.kondenko.yamblzweather.ui.settings.SettingsActivity;
import com.kondenko.yamblzweather.utils.Logger;
import com.kondenko.yamblzweather.utils.WeatherUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.Observable;

public class WeatherActivity extends BaseMvpActivity<WeatherViewModel, WeatherPresenter> implements WeatherView {

    private static final java.lang.String TAG = "WeatherActivity";
    @BindView(R.id.weather_button_city)
    public Spinner spinnerCity;
    @BindView(R.id.weather_text_temperature)
    public TextView textTemperature;
    @BindView(R.id.weather_icon_condition)
    public WeatherIconView weatherIcon;
    @BindView(R.id.weather_text_condition)
    public TextView textCondition;
    @BindView(R.id.weather_refresh_layout)
    public SwipeRefreshLayout refreshLayout;
    @BindView(R.id.weather_text_latest_update)
    public TextView textLatestUpdate;
    @BindView(R.id.text_rain_level_header)
    public TextView textRainLevelHeader;
    @BindView(R.id.text_rain_level_value)
    public TextView textRainLevel;
    @BindView(R.id.text_wind_value)
    public TextView textWindSpeed;
    @BindView(R.id.forecast_container)
    public RecyclerView forecastView;

    private ArrayAdapter<City> spinnerAdapter;
    private ForecastAdapter forecastAdapter;

    @Inject
    public void Inject(WeatherPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.updateData();
    }

    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(!defaultSharedPreferences.getBoolean(getString(R.string.key_onboarding_completion), false)){
            startActivity(new Intent(this, OnboardingActivity.class));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_weather);
        ButterKnife.bind(this);
        AndroidInjection.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setToolbar(toolbar, false);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayShowTitleEnabled(false);
        refreshLayout.setOnRefreshListener(presenter::updateData);

        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.add(getCityEditorObject());
        spinnerCity.setAdapter(spinnerAdapter);

        forecastAdapter = new ForecastAdapter(new ArrayList<>(), this);
        forecastView.setAdapter(forecastAdapter);
        forecastView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        forecastView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @NonNull
    private City getCityEditorObject() {
        return City.create(Location.builder().latitude(0).longitude(0).build(), getString(R.string.edit_city_list_button_text), "ID");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings: {
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            }
            default: {
                throw new IllegalArgumentException("Wrong menu item used");
            }
        }
        return true;
    }

    @Override
    public void setData(WeatherViewModel weather) {
        super.setData(weather);
        showCityList(weather.cities().cities(), weather.city());
        showTemperature(weather.weather().temperature(), weather.tempUnit());
        showCondition(weather.weather().weatherConditions());
        showWindSpeed(weather.weather().windSpeed());
        showForecast(weather.forecast().weatherList());
    }

    private void showForecast(List<Weather> weathers) {
        forecastAdapter.setWeather(weathers);
    }

    private void showCityList(List<City> cities, City city) {
        spinnerAdapter.clear();
        cities.add(getCityEditorObject());
        spinnerAdapter.addAll(cities);
        spinnerAdapter.notifyDataSetChanged();
        spinnerCity.setSelection(cities.indexOf(city));
    }

    @Override
    public void showLoading(boolean loading) {
        refreshLayout.setRefreshing(loading);
    }

    @Override
    public void showError(Throwable error) {
        Logger.w(TAG, error);
        Toast.makeText(this, this.getString(R.string.error_loading_weather), Toast.LENGTH_LONG).show();
    }

    // Precise data formatting

    private void showTemperature(Temperature temperature, TempUnit units) {
        double temp = 0;
        switch (units) {
            case IMPERIAL:
                temp = temperature.fahrenheitDegrees();
                break;
            case METRIC:
                temp = temperature.celsiusDegrees();
                break;
            case SCIENTIFIC:
                temp = temperature.kelvinDegrees();
                break;
        }
        String tempText = String.format(Locale.getDefault(), "%.1f°%s", temp, units.getUnitLetter());
        textTemperature.setText(tempText);
    }

    private void showCondition(WeatherConditions condition) {
        weatherIcon.setIconResource(getString(WeatherUtils.getIconStringResource(condition)));
    }


    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    private void showWindSpeed(double value) {
        String windSpeed = getString(R.string.weather_wind_speed_value, value);
        textWindSpeed.setText(windSpeed);
    }

    @Override
    public void showLatestUpdate(String latestUpdateTime) {
        String latestUpdateTimeString = getString(R.string.weather_latest_update_time_value, latestUpdateTime);
        textLatestUpdate.setText(latestUpdateTimeString);
    }

    @Override
    public Observable<City> getCitySelections() {
        return RxAdapterView.itemSelections(spinnerCity)
                            .skipInitialValue()
                            .doOnNext(x -> {
                                if (x != null && x == spinnerAdapter.getCount() - 1) {
                                   startActivity(new Intent(this, SuggestsActivity.class));
                                }
                            })
                            .filter(x -> x != spinnerAdapter.getCount() - 1)
                            .map(spinnerAdapter::getItem)
                            .distinctUntilChanged();
    }


}
