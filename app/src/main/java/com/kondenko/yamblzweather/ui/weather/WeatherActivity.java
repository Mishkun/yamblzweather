package com.kondenko.yamblzweather.ui.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pwittchen.weathericonview.WeatherIconView;
import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.model.entity.Weather;
import com.kondenko.yamblzweather.ui.BaseActivity;
import com.kondenko.yamblzweather.ui.about.AboutActivity;
import com.kondenko.yamblzweather.ui.settings.SettingsActivity;
import com.kondenko.yamblzweather.utils.L;
import com.kondenko.yamblzweather.utils.WeatherUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class WeatherActivity extends BaseActivity
        implements WeatherView {

    private static final java.lang.String TAG = "WeatherActivity";

    @Inject
    public WeatherPresenter presenter;

    @BindView(R.id.weather_button_city)
    public Button buttonCity;

    @BindView(R.id.text_temperature)
    public TextView textTemperature;

    @BindView(R.id.weather_icon)
    public WeatherIconView weatherIcon;

    @BindView(R.id.text_condition)
    public TextView textCondition;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(R.layout.layout_weather);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setToolbar(toolbar, false);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onAttach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onDetach();
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
            case R.id.action_about: {
                startActivity(new Intent(this, AboutActivity.class));
                break;
            }
            default: {
                throw new IllegalArgumentException("Wrong menu item used");
            }
        }
        return true;
    }

    @Override
    public void showCity(String city) {
        buttonCity.setText(city);
    }

    @Override
    public void showTemperature(double temp, String units) {
        String temperature = String.valueOf(Math.round(temp));
        Spannable temperatureSpannable = WeatherUtils.getTemperatureString(this, temperature, units);
        textTemperature.setText(temperatureSpannable);
    }

    @Override
    public void showCondition(List<Weather> condition) {
        Weather weatherCondition = condition.get(0);
        weatherIcon.setIconResource(getString(WeatherUtils.getIconStringResource(weatherCondition)));
        String description = weatherCondition.getDescription();
        description = description.substring(0, 1).toUpperCase() + description.substring(1);
        textCondition.setText(description);
    }

    @Override
    public void showError(Throwable error) {
        L.w(TAG, error);
        Toast.makeText(this, this.getString(R.string.error_loading_weather), Toast.LENGTH_LONG).show();
    }
}
