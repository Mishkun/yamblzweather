package com.kondenko.yamblzweather.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mishkun on 27.07.2017.
 */

public class CitySearchResult {

    public Result getResult() {
        return result;
    }

    @SerializedName("result")
    Result result;

    public static class Result {
        @SerializedName("geometry")
        Geometry geometry;

        public Geometry getGeometry() {
            return geometry;
        }

        public static class Geometry {
            @SerializedName("location")
            Coord coordinates;

            public Coord getCoordinates() {
                return coordinates;
            }
        }
    }
}
