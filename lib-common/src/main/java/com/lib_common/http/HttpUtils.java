package com.lib_common.http;

import androidx.lifecycle.LifecycleOwner;

import com.lib_common.entity.Friend;
import com.rxjava.rxlife.RxLife;

import java.util.List;

import rxhttp.RxHttp;

/**
 * Http接口请求工具类
 * created by yhw
 * date 2022/11/8
 */
public class HttpUtils {

    /**
     * 常用网站
     */
    public static void friend(LifecycleOwner owner, HttpListener<List<Friend>> httpListener) {
        RxHttp.get(Api.FRIEND)
                .toObservableResponseList(Friend.class)
                .as(RxLife.asOnMain(owner))
                .subscribe(httpListener::onSuccess, (OnError) error ->
                        httpListener.onFail(error.getErrorCode(), error.getErrorMsg())
                );
    }


}
