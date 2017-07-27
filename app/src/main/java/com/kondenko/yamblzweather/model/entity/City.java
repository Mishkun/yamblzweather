package com.kondenko.yamblzweather.model.entity;

/**
 * Created by Mishkun on 27.07.2017.
 */

public class City {
    private final String city;
    private final Coord coordinates;

    public City(Coord coords, String city) {
        this.city = city;
        this.coordinates = coords;
    }

    public String getCity() {
        return city;
    }

    public Coord getCoordinates() {
        return coordinates;
    }
}
