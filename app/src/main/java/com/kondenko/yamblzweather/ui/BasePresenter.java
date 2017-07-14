package com.kondenko.yamblzweather.ui;

import android.view.View;

import com.kondenko.yamblzweather.utils.lifecycle.PresenterEvent;
import com.kondenko.yamblzweather.utils.lifecycle.RxLifecyclePresenter;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public abstract class BasePresenter<V extends BaseView, I extends BaseInteractor>
        implements LifecycleProvider<Integer> {

    protected final BehaviorSubject<Integer> lifecycleSubject = BehaviorSubject.create();

    protected V view;
    protected I interactor;

    public BasePresenter(V view, I interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    public void onAttach(V view) {
        this.view = view;
        lifecycleSubject.onNext(PresenterEvent.ATTACH);
    }

    public void onDetach() {
        lifecycleSubject.onNext(PresenterEvent.DETACH);
        view = null;
    }

    @Nonnull
    @Override
    public Observable<Integer> lifecycle() {
        return lifecycleSubject;
    }

    @Nonnull
    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(@Nonnull Integer event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Nonnull
    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecyclePresenter.bindPresenter(lifecycleSubject);
    }
}
