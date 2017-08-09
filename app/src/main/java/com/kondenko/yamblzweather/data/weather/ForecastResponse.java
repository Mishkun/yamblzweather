package com.kondenko.yamblzweather.data.weather;

import com.google.gson.annotations.SerializedName;
import com.kondenko.yamblzweather.data.weather.WeatherModel.WeatherCondition;

import java.util.List;

/**
 * Created by Mishkun on 04.08.2017.
 */

class ForecastResponse {
    @SerializedName("list")
    private List<ForecastWeather> list;

    public List<ForecastWeather> getList() {
        return list;
    }


    static class ForecastWeather {
        @SerializedName("dt")
        private long dt;

        @SerializedName("temp")
        private Temp temp;

        @SerializedName("pressure")
        private double pressure;

        @SerializedName("humidity")
        private int humidity;

        @SerializedName("weather")
        private List<WeatherCondition> weatherCondition;

        long getDt() {
            return dt;
        }

        Temp getTemp() {
            return temp;
        }

        double getPressure() {
            return pressure;
        }

        int getHumidity() {
            return humidity;
        }

        List<WeatherCondition> getWeatherCondition() {
            return weatherCondition;
        }


        static class Temp {

            @SerializedName("day")
            private double day;

            @SerializedName("min")
            private double min;

            @SerializedName("max")
            private double max;

            @SerializedName("night")
            private double night;

            @SerializedName("eve")
            private double eve;

            @SerializedName("morn")
            private double morn;

            double getDay() {
                return day;
            }


            double getMin() {
                return min;
            }

            double getMax() {
                return max;
            }


            double getNight() {
                return night;
            }


            double getEve() {
                return eve;
            }


            double getMorn() {
                return morn;
            }

        }
    }

}

