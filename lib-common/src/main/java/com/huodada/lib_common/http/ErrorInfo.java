package com.huodada.lib_common.http;

import android.accounts.NetworkErrorException;
import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;

import rxhttp.wrapper.exception.HttpStatusCodeException;
import rxhttp.wrapper.exception.ParseException;

/**
 * 接口错误对象
 */
public class ErrorInfo {
    private int errorCode;  //仅指服务器返回的错误码
    private String errorMsg; //错误文案，网络错误、请求失败错误、服务器返回的错误等文案
    private final Throwable throwable; //异常信息

    public ErrorInfo(Throwable throwable) {
        this.throwable = throwable;
        if (throwable.getLocalizedMessage() != null) {
            this.errorCode = Integer.parseInt(throwable.getLocalizedMessage());
        }
        if (throwable instanceof HttpStatusCodeException) { //请求失败异常
            if (this.errorCode == 404) {
                this.errorMsg = "接口地址错误";
            } else if (this.errorCode >= 500) {
                this.errorMsg = "服务器错误，请稍后再试";
            } else {
                this.errorMsg = "接口请求错误";
            }
        } else if (throwable instanceof NetworkErrorException || throwable instanceof ConnectException) {
            this.errorMsg = "网络错误，请检查网络";
        } else if (throwable instanceof JsonSyntaxException) { //请求成功，但Json语法异常,导致解析失败
            this.errorMsg = "数据解析失败";
        } else if (throwable instanceof ParseException) { // ParseException异常表明请求成功，但是数据不正确
            this.errorMsg = throwable.getMessage();
        }
        if (TextUtils.isEmpty(errorMsg)) {
            this.errorMsg = "未知错误，请重试";
        }
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        if (null == errorMsg) {
            errorMsg = "";
        }
        return errorMsg;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
