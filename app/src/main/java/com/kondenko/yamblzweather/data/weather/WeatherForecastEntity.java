package com.kondenko.yamblzweather.data.weather;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Mishkun on 05.08.2017.
 */
@Entity(tableName = "weather_forecast",primaryKeys = {"forecast", "timestamp"}, foreignKeys = @ForeignKey(entity = ForecastEntity.class, parentColumns = "city", childColumns = "forecast", onDelete = CASCADE))
public class WeatherForecastEntity {
    @ColumnInfo(name = "timestamp")
    private long timestamp;

    @ColumnInfo(name = "forecast")
    private String forecast;

    @ColumnInfo(name = "weather_condition_code")
    private int weatherConditionCode;

    @ColumnInfo(name = "temperature")
    private double temperature;

    @ColumnInfo(name = "humidity")
    private double humidity;

    @ColumnInfo(name = "pressure")
    private double pressure;

    @ColumnInfo(name = "wind_speed")
    private double windSpeed;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }

    public int getWeatherConditionCode() {
        return weatherConditionCode;
    }

    public void setWeatherConditionCode(int weatherConditionCode) {
        this.weatherConditionCode = weatherConditionCode;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
}
