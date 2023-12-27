package com.yhw.pgyer.http;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

import com.lib_common.http.HttpListener;
import com.lib_common.http.OnError;
import com.rxjava.rxlife.RxLife;
import com.yhw.pgyer.Constants;
import com.yhw.pgyer.bean.App;
import com.yhw.pgyer.http.parser.ResponsePgyerParser;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import rxhttp.RxHttp;

/**
 * created by yhw
 * date 2023/9/21
 */
public class HttpRequest {
    /**
     * 获取APP列表
     */
    public static void getAppList(LifecycleOwner owner, int page, HttpListener<App> httpListener) {
        Log.i("TAG","APP_KEY===> " + Constants.APP_KEY);
        Map<String,Object> req = new HashMap<>();
        req.put("page", page);
        req.put("_api_key", Constants.API_KEY);
        req.put("appKey", Constants.APP_KEY);
        RxHttp.postForm(Api.app_list)
                .addAll(req)
                .setDomainIfAbsent(Api.pgyer)
                .setHeader("Content-Type", "application/x-www-form-urlencoded")
                .toObservable(new ResponsePgyerParser<App>() {
                })
                .as(RxLife.asOnMain(owner))
                .subscribe(httpListener::onSuccess, (OnError) error ->
                        httpListener.onFail(error.getErrorCode(), error.getErrorMsg())
                );
    }

    /**
     * 获取账号下的APP列表
     */
    public static void getMyAppList(LifecycleOwner owner, int page, HttpListener<App> httpListener) {
        Map<String,Object> req = new HashMap<>();
        req.put("page", page);
        req.put("_api_key", Constants.API_KEY);
        RxHttp.postForm(Api.my_app_list)
                .addAll(req)
                .setDomainIfAbsent(Api.pgyer)
                .setHeader("Content-Type", "application/x-www-form-urlencoded")
                .toObservable(new ResponsePgyerParser<App>() {
                })
                .as(RxLife.asOnMain(owner))
                .subscribe(httpListener::onSuccess, (OnError) error ->
                        httpListener.onFail(error.getErrorCode(), error.getErrorMsg())
                );
    }


    /**
     * 检测本APP是否有更新
     */
    public static void checkAppUpgrade(LifecycleOwner owner, HttpListener<App.AppInfo> httpListener) {
        Map<String,Object> req = new HashMap<>();
        req.put("appKey", Constants.MYSELF_APP_KEY);
        req.put("_api_key", Constants.API_KEY);
        RxHttp.postForm(Api.check_app_upgrade)
                .addAll(req)
                .setDomainIfAbsent(Api.pgyer)
                .setHeader("Content-Type", "application/x-www-form-urlencoded")
                .toObservable(new ResponsePgyerParser<App.AppInfo>() {
                })
                .as(RxLife.asOnMain(owner))
                .subscribe(httpListener::onSuccess, (OnError) error ->
                        httpListener.onFail(error.getErrorCode(), error.getErrorMsg())
                );
    }

    /**
     * 安装
     */
    public static String installApp(String buildKey) {
        return Api.pgyer + Api.app_install + "?_api_key=" + Constants.API_KEY + "&buildKey=" + buildKey + "&buildPassword="+Constants.INSTALL_PASSWORD;
    }

    /**
     * 下载文件
     *
     * @param downUrl 文件地址
     */
    public static Disposable downFile(LifecycleOwner owner, String downUrl, String descPath, HttpListener<String> httpListener) {
        return RxHttp.get(downUrl)
                .toDownloadObservable(descPath, false)
                .onMainProgress(progress -> {
                    Log.i("TAG", "progress==> " +  progress);
                    httpListener.onMainProgress(progress);
                })
                .as(RxLife.asOnMain(owner))
                .subscribe(httpListener::onSuccess, (OnError) error ->
                        httpListener.onFail(error.getErrorCode(), error.getErrorMsg())
                );
    }
}
