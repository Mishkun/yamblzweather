package com.kondenko.yamblzweather.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mishkun on 27.07.2017.
 */

public class Prediction {
    @SerializedName("description")
    String place;
    @SerializedName("place_id")
    String id;

    public String getPlace() {
        return place;
    }

    public String getId() {
        return id;
    }
}
