package com.app.bean;

/**
 * LiveData
 * created by yhw
 * date 2023/8/3
 */
public class Account {
    private String accountName;
    private String headUrl;

    public Account() {
    }

    public Account(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
}
