package com.example.cognitev.nearby.base;

import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Salma on 9/21/2017.
 */

public abstract class BaseViewModel extends ViewModel implements BaseContract.BaseViewModel{
    protected CompositeDisposable subscriptions = new CompositeDisposable();


    @Override
    public void cancelAllRequests() {
        subscriptions.clear();
    }

    @Override
    protected void onCleared() {
        cancelAllRequests();
    }
}
