package com.app.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.app.BR;

/**
 * LiveData
 * created by yhw
 * date 2023/8/3
 */
public class Account extends BaseObservable {
    private String accountName;
    private String headUrl;
    private long createTime;

    public Account() {
    }

    public Account(String accountName) {
        this.accountName = accountName;
    }

    @Bindable
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
        this.notifyPropertyChanged(BR.accountName);
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
