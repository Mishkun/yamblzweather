package com.kondenko.yamblzweather.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;

import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.model.entity.Weather;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WeatherUtils {

    private static final Map<InclusiveRange, Integer> conditionsMap;

    static {
        // Mapping weather code to icons
        // See http://openweathermap.org/weather-conditions
        // See http://erikflowers.github.io/weather-icons/
        Map<InclusiveRange, Integer> tempMap = new HashMap<>();
        tempMap.put(new InclusiveRange(200, 232), R.string.wi_thunderstorm);
        tempMap.put(new InclusiveRange(300, 321), R.string.wi_showers);
        tempMap.put(new InclusiveRange(500, 531), R.string.wi_rain);
        tempMap.put(new InclusiveRange(600, 622), R.string.wi_snow);
        tempMap.put(new InclusiveRange(700, 781), R.string.wi_fog);
        tempMap.put(new InclusiveRange(800), R.string.wi_day_sunny);
        tempMap.put(new InclusiveRange(801, 804), R.string.wi_cloud);
        conditionsMap = Collections.unmodifiableMap(tempMap);
    }

    /**
     * Returns an appropriate string resource which is needed to display an icon inRange {@link com.github.pwittchen.weathericonview.WeatherIconView}
     *
     * @param weather weather condition
     * @return resource id of the icon
     */
    public static int getIconStringResource(Weather weather) {
        int id = weather.getId();
        for (InclusiveRange codes : conditionsMap.keySet()) {
            if (codes.inRange(id)) return conditionsMap.get(codes);
        }
        return R.string.wi_na;
    }

    public static Spannable getTemperatureString(Context context, String temperature, String units) {
        char unitLetter = units.substring(0, 1).toUpperCase().charAt(0);
        String temperatureString = context.getString(R.string.weather_temperature_value, temperature, unitLetter);
        Spannable temperatureSpannable = new SpannableString(temperatureString);
        return temperatureSpannable;
    }

}
