package com.lib_common.http;

/**
 * http接口请求结果回调
 * created by yhw
 * date 2022/11/9
 */
public interface HttpListener<T> {
    /**
     * 接口请求成功
     *
     * @param t 返回的实体泛型
     */
    void onSuccess(T t);

    /**
     * 接口请求失败
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    default void onFail(int errorCode, String errorMsg) {

    }
}
