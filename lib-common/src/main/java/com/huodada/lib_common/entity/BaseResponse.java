package com.huodada.lib_common.entity;

import java.io.Serializable;

/**
 * 网络请求响应基类
 * @param <T> 泛型
 */
public class BaseResponse<T> implements Serializable {
    private int errorCode;
    private String errorMsg;
    private T data;
    private String logCode;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getLogCode() {
        return logCode;
    }

    public void setLogCode(String logCode) {
        this.logCode = logCode;
    }
}
