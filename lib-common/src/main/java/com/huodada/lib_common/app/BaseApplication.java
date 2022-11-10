package com.huodada.lib_common.app;

import android.util.Log;
import android.view.Gravity;

import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.toast.ToastUtils;
import com.huodada.lib_common.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import rxhttp.RxHttpPlugins;
import rxhttp.wrapper.param.Method;

/**
 * created by yhw
 * date 2022/11/8
 */
public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initSDK();
    }

    private void initSDK() {
        initARouter();
        initToast();
        initRxHttp();
    }

    /**
     * 初始化 Toast 框架
     */
    private void initToast() {
        ToastUtils.init(this);
        ToastUtils.setGravity(Gravity.CENTER);
    }

    /**
     * 初始化RxHttp
     */
    private void initRxHttp() {
        RxHttpPlugins.init(genericClient())
                .setDebug(BuildConfig.DEBUG)
                .setOnParamAssembly(p -> {
                    Method method = p.getMethod();
                    if (method.isGet()) { //Get请求

                    } else if (method.isPost()) { //Post请求

                    }
                    p.addHeader("systemType", "android"); //添加公共请求头
                })
                //替换 data:"" 为"",防止对象转换失败
                .setResultDecoder(result -> result.replace(",\"data\":\"\"", ""));
    }

    private OkHttpClient genericClient() {
        final int timeout = 60;
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(timeout, TimeUnit.SECONDS);
        builder.readTimeout(timeout, TimeUnit.SECONDS);
        builder.writeTimeout(timeout, TimeUnit.SECONDS);
        //错误重连
//        builder.retryOnConnectionFailure(false);
        //新建log拦截器
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message ->
                Log.w("rxhttp", "retrofitBack = " + message));
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BASIC;
        loggingInterceptor.setLevel(level);
        builder.addInterceptor(loggingInterceptor);//添加retrofit日志打印 }
//        builder.addInterceptor(new BaseUrlInterceptor());
//        builder.addInterceptor(new HeaderInterceptor());
        return builder.build();
    }

    /**
     * 初始化ARouter框架
     */
    private void initARouter() {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (BuildConfig.DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }
}
