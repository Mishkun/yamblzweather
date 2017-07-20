package com.kondenko.yamblzweather.ui;

public interface BaseView<M> {

    public void setData(M data);

    public void showLoading(boolean loading);

    public void showError(Throwable error);

}
