package com.kondenko.yamblzweather.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mishkun on 27.07.2017.
 */

public class CitySuggest {
    @SerializedName("predictions")
    private List<Prediction> predictions;

    public List<Prediction> getPredictions() {
        return predictions;
    }
}
