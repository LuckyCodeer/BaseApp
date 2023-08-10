package com.app.demo;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.ViewModel;

import com.app.BR;
import com.hjq.toast.ToastUtils;

public class MyViewModel extends ViewModel {

    public void showMsg() {
        ToastUtils.show("点击了");
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
