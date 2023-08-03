package com.app.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.app.BR;

/**
 * 实现 BaseObservable 的方式
 * created by yhw
 * date 2023/8/3
 */
public class User extends BaseObservable {
    private String name;
    private String gender;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
        notifyPropertyChanged(BR.gender);
    }
}
