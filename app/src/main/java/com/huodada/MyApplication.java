package com.huodada;

import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.huodada.lib_common.app.BaseApplication;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * created by yhw
 * date 2022/12/9
 */
public class MyApplication extends BaseApplication {
    private static final String TAG = "MyApplication";

    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);

    private final Thread.UncaughtExceptionHandler exceptionHandler = (thread, throwable) -> {
        Log.i(TAG, "throwable==>" + throwable);
        Log.i(TAG, "getCause==>" + throwable.getCause());
        throwable.printStackTrace();
        try {
            executor.execute(() -> {
                Looper.prepare();
                Toast toast = Toast.makeText(getApplicationContext(), "系统内部发生异常，即将重启", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0 , 0);
                toast.show();
                Looper.loop();
            });
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        restartApp();
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "MyApplication");
//        if (!BuildConfig.DEBUG){
            Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);
//        }
    }

    /**
     * 重启应用
     */
    public void restartApp() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(1);

        // 重启应用
//        getApplicationContext().startActivity(getApplicationContext().getPackageManager().getLaunchIntentForPackage(getApplicationContext().getPackageName()));
//        //干掉当前的程序
//        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
