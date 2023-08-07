package com.lib_common.base.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.MutableLiveData;

/**
 * 基类ViewModel
 * created by yhw
 * date 2023/8/4
 */
public class BaseViewModel extends AndroidViewModel implements DefaultLifecycleObserver {
    private final MutableLiveData<Boolean> mShowLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> mLoadingMsg = new MutableLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getShowLoading() {
        return mShowLoading;
    }

    public MutableLiveData<String> getLoadingMsg() {
        return mLoadingMsg;
    }

    public void showLoading() {
        this.mShowLoading.postValue(true);
    }

    public void showLoading(String msg) {
        this.mLoadingMsg.postValue(msg);
    }

    public void dismissLoading() {
        this.mShowLoading.postValue(false);
    }

}
