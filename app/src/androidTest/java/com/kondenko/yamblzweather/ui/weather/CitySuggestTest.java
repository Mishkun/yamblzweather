package com.kondenko.yamblzweather.ui.weather;


import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.kondenko.yamblzweather.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@SmallTest
@RunWith(AndroidJUnit4.class)
public class CitySuggestTest {

    private static final String TEXT1 = "Оймякон";
    private static final String TEXT2 = "Тольятти";
    @Rule
    public ActivityTestRule<WeatherActivity> mActivityTestRule = new ActivityTestRule<>(WeatherActivity.class);

    @Test
    public void citySuggestTest() throws Exception {
        onView(withId(R.id.weather_button_city)).perform(click());
        onView(withId(R.id.search_field)).perform(replaceText(TEXT1), closeSoftKeyboard());
        TimeUnit.MILLISECONDS.sleep(500);
        onView(withId(R.id.suggests_view)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.weather_button_city)).check(matches(withText(TEXT1)));

        onView(withId(R.id.weather_button_city)).perform(click());
        onView(withId(R.id.search_field)).perform(replaceText(TEXT2), closeSoftKeyboard());
        TimeUnit.MILLISECONDS.sleep(500);
        onView(withId(R.id.suggests_view)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.weather_button_city)).check(matches(withText(TEXT2)));
    }
}
