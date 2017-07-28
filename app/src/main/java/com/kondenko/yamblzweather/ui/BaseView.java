package com.kondenko.yamblzweather.ui;

import android.os.Parcelable;

public interface BaseView<M extends Parcelable> {

    public void setData(M data);

    public void showLoading(boolean loading);

    public void showError(Throwable error);

}
