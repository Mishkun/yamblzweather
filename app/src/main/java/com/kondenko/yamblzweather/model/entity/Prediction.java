package com.kondenko.yamblzweather.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mishkun on 27.07.2017.
 */

public class Prediction implements Parcelable{
    @SerializedName("description")
    String place;
    @SerializedName("place_id")
    String id;

    protected Prediction(Parcel in) {
        place = in.readString();
        id = in.readString();
    }

    public static final Creator<Prediction> CREATOR = new Creator<Prediction>() {
        @Override
        public Prediction createFromParcel(Parcel in) {
            return new Prediction(in);
        }

        @Override
        public Prediction[] newArray(int size) {
            return new Prediction[size];
        }
    };

    public String getPlace() {
        return place;
    }

    public String getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(place);
        dest.writeString(id);
    }
}
