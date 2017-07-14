package com.kondenko.yamblzweather.ui.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.ui.about.FragmentAbout;
import com.kondenko.yamblzweather.ui.settings.FragmentSettings;
import com.kondenko.yamblzweather.ui.weather.FragmentWeather;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//    @Inject
//    public DispatchingAndroidInjector<Fragment> fragmentInjector;

    private DrawerLayout drawer;
    private FragmentWeather fragmentWeather = new FragmentWeather();
    private FragmentSettings fragmentSettings = new FragmentSettings();
    private FragmentAbout fragmentAbout = new FragmentAbout();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_main);
        setupDrawer();
        setFragment(fragmentWeather);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_weather: {
                setFragment(fragmentWeather);
                setTitle(R.string.title_weather);
                break;
            }
            case R.id.nav_settings: {
                setFragment(fragmentSettings);
                setTitle(R.string.title_settings);
                break;
            }
            case R.id.nav_about: {
                setFragment(fragmentAbout);
                setTitle(R.string.title_about);
                break;
            }
            default: {
                throw new IllegalArgumentException("Wrong drawer item used");
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    private void setupDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.all_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


//    @Override
//    public AndroidInjector<Fragment> supportFragmentInjector() {
//        return fragmentInjector;
//    }

}
