package com.kondenko.yamblzweather.data.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

class WeatherModel {

    @SerializedName("weather")
    @Expose
    private List<WeatherCondition> weatherCondition;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;


    List<WeatherCondition> getWeatherCondition() {
        return weatherCondition;
    }


    String getBase() {
        return base;
    }


    Main getMain() {
        return main;
    }


    Wind getWind() {
        return wind;
    }


    Clouds getClouds() {
        return clouds;
    }




    static class WeatherCondition {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("main")
        @Expose
        private String main;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("icon")
        @Expose
        private String icon;

        int getId() {
            return id;
        }


        String getMain() {
            return main;
        }


        String getDescription() {
            return description;
        }


        String getIcon() {
            return icon;
        }


    }

    long getDt() {
        return dt;
    }

    @SerializedName("dt")
    private long dt;


    static class Sys {

        @SerializedName("type")
        @Expose
        private int type;
        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("message")
        @Expose
        private double message;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("sunrise")
        @Expose
        private int sunrise;
        @SerializedName("sunset")
        @Expose
        private int sunset;

        int getType() {
            return type;
        }


        int getId() {
            return id;
        }


        double getMessage() {
            return message;
        }


        String getCountry() {
            return country;
        }


        int getSunrise() {
            return sunrise;
        }


        int getSunset() {
            return sunset;
        }


    }

    static class Main {

        @SerializedName("temp")
        @Expose
        private double temp;
        @SerializedName("pressure")
        @Expose
        private double pressure;
        @SerializedName("humidity")
        @Expose
        private double humidity;

        double getTemp() {
            return temp;
        }


        double getPressure() {
            return pressure;
        }


        double getHumidity() {
            return humidity;
        }


    }

    static class Clouds {

        @SerializedName("all")
        @Expose
        private int all;

        int getAll() {
            return all;
        }


    }

    static class Wind {

        @SerializedName("speed")
        @Expose
        private double speed;
        @SerializedName("deg")
        @Expose
        private double deg;

        double getSpeed() {
            return speed;
        }

        double getDeg() {
            return deg;
        }
    }
}
