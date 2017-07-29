package com.kondenko.yamblzweather.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mishkun on 27.07.2017.
 */

public class CitySuggest implements Parcelable{
    @SerializedName("predictions")
    private List<Prediction> predictions;

    protected CitySuggest(Parcel in) {
        predictions = in.createTypedArrayList(Prediction.CREATOR);
    }

    public static final Creator<CitySuggest> CREATOR = new Creator<CitySuggest>() {
        @Override
        public CitySuggest createFromParcel(Parcel in) {
            return new CitySuggest(in);
        }

        @Override
        public CitySuggest[] newArray(int size) {
            return new CitySuggest[size];
        }
    };

    public List<Prediction> getPredictions() {
        return predictions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(predictions);
    }
}
