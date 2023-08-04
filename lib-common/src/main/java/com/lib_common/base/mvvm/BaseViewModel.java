package com.lib_common.base.mvvm;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * created by yhw
 * date 2023/8/4
 */
public class BaseViewModel extends ViewModel implements DefaultLifecycleObserver {
    private final MutableLiveData<Boolean> mShowLoading = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> getShowLoading() {
        return mShowLoading;
    }

    public void showLoading() {
        this.mShowLoading.postValue(true);
    }

    public void dismissLoading() {
        this.mShowLoading.postValue(false);
    }

}
