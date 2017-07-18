
package com.kondenko.yamblzweather.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind implements Parcelable
{

    @SerializedName("speed")
    @Expose
    private double speed;
    @SerializedName("deg")
    @Expose
    private int deg;
    public final static Creator<Wind> CREATOR = new Creator<Wind>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Wind createFromParcel(Parcel in) {
            Wind instance = new Wind();
            instance.speed = ((double) in.readValue((double.class.getClassLoader())));
            instance.deg = ((int) in.readValue((int.class.getClassLoader())));
            return instance;
        }

        public Wind[] newArray(int size) {
            return (new Wind[size]);
        }

    }
    ;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(speed);
        dest.writeValue(deg);
    }

    public int describeContents() {
        return  0;
    }

}
