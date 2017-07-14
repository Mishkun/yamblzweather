package com.kondenko.yamblzweather.ui.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.model.entity.WeatherData;
import com.kondenko.yamblzweather.ui.BaseActivity;
import com.kondenko.yamblzweather.ui.about.AboutActivity;
import com.kondenko.yamblzweather.ui.settings.SettingsActivity;
import com.kondenko.yamblzweather.utils.L;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class WeatherActivity extends BaseActivity
        implements WeatherView {

    private static final String TAG = "WeatherActivity";

    @Inject
    public WeatherPresenter presenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(R.layout.layout_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setToolbar(toolbar, false);
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
    public void showWeather(WeatherData weatherData) {
        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(Throwable error) {
        L.w(TAG, error);
        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
    }
}
