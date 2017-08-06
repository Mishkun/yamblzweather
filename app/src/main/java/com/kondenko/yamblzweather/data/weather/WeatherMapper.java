package com.kondenko.yamblzweather.data.weather;

import com.kondenko.yamblzweather.domain.entity.Temperature;
import com.kondenko.yamblzweather.domain.entity.Weather;
import com.kondenko.yamblzweather.domain.entity.WeatherConditions;

/**
 * Created by Mishkun on 04.08.2017.
 */

class WeatherMapper {
    static WeatherEntity responseToWeatherdb(WeatherModel weatherModel) {
        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setHumidity(weatherModel.getMain().getHumidity());
        weatherEntity.setPressure(weatherModel.getMain().getPressure());
        weatherEntity.setTemperature(weatherModel.getMain().getTemp());
        weatherEntity.setWindSpeed(weatherModel.getWind().getSpeed());
        weatherEntity.setWeatherConditionCode(weatherModel.getWeatherCondition().get(0).getId());
        return weatherEntity;
    }

    static WeatherForecastEntity responseToWeatherForecastdb(WeatherModel weatherModel) {
        WeatherForecastEntity weatherEntity = new WeatherForecastEntity();
        weatherEntity.setHumidity(weatherModel.getMain().getHumidity());
        weatherEntity.setPressure(weatherModel.getMain().getPressure());
        weatherEntity.setTemperature(weatherModel.getMain().getTemp());
        weatherEntity.setWindSpeed(weatherModel.getWind().getSpeed());
        weatherEntity.setTimestamp(weatherModel.getDt());
        weatherEntity.setWeatherConditionCode(weatherModel.getWeatherCondition().get(0).getId());
        return weatherEntity;
    }

    // function to convert hectopascal to millimeter mercury. Formula from here: http://www.convertunits.com/from/mm+Hg/to/hPa
    private static double hPaToMmHg(double pressureHPa) {
        return pressureHPa * 0.750061561303;
    }

    private static WeatherConditions codeToCondition(int code) {
        if (code >= 200 && code < 300) {
            return WeatherConditions.THUNDERSTORM;
        } else if (code >= 300 && code < 400) {
            return WeatherConditions.DRIZZLE;
        } else if (code >= 500 && code < 600) {
            return WeatherConditions.RAIN;
        } else if (code >= 600 && code < 700) {
            return WeatherConditions.SNOW;
        } else if (code >= 700 && code < 800) {
            return WeatherConditions.FOG;
        } else if (code >= 800 && code < 803) {
            return WeatherConditions.PARTLY_CLOUDY;
        } else if (code >= 803 && code < 900) {
            return WeatherConditions.CLOUDY;
        } else {
            return WeatherConditions.CLEAR;
        }
    }


    static Weather dbToDomain(WeatherForecastEntity weatherForecastEntity) {
        return Weather.builder()
                      .timestamp(weatherForecastEntity.getTimestamp())
                      .temperature(Temperature.valueOfKelvin(weatherForecastEntity.getTemperature()))
                      .weatherConditions(codeToCondition(weatherForecastEntity.getWeatherConditionCode()))
                      .pressure(weatherForecastEntity.getPressure())
                      .humidity(weatherForecastEntity.getHumidity())
                      .windSpeed(weatherForecastEntity.getWindSpeed())
                      .build();
    }

    static Weather dbToDomain(WeatherEntity weatherEntity) {
        return Weather.builder()
                      .timestamp(weatherEntity.getTimestamp())
                      .temperature(Temperature.valueOfKelvin(weatherEntity.getTemperature()))
                      .weatherConditions(codeToCondition(weatherEntity.getWeatherConditionCode()))
                      .pressure(weatherEntity.getPressure())
                      .humidity(weatherEntity.getHumidity())
                      .windSpeed(weatherEntity.getWindSpeed())
                      .build();
    }
}
