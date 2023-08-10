package com.app.mvvm;

import android.app.Application;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.bean.Account;
import com.hjq.toast.ToastUtils;
import com.lib_common.base.mvvm.BaseViewModel;

/**
 * created by yhw
 * date 2023/8/3
 */
public class DemoViewModel extends BaseViewModel {
    private final MutableLiveData<Account> mAccount = new MutableLiveData<>();
    private final MutableLiveData<Long> mCountDown = new MutableLiveData<>();
    private final MutableLiveData<Integer> mColorType = new MutableLiveData<>();
    private CountDownTimer mCountDownTimer;
    private final Handler mHandler = new Handler();

    public DemoViewModel(@NonNull Application application) {
        super(application);
    }

    public void getData() {
        showLoading("查询中...");
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Account account = new Account("账户名" + Math.random());
            account.setHeadUrl("https://mmbiz.qpic.cn/mmbiz/MOu2ZNAwZwOWcVeCuqAe6AhPAGjaLm4E3ZhcuhgE19sE9JaQm53AEriaibbE555XCWIbiaibKUUujwLHHJ2VAJy0SA/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1&wx_co=1");
            mAccount.postValue(account);
            dismissLoading();
        }).start();
    }

    /**
     * 获取验证码
     */
    public void getVerifyCode() {
        if (mCountDown.getValue() != null && mCountDown.getValue() != 0) {
            return;
        }
        showLoading("获取中...");
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            dismissLoading();
            ToastUtils.show("获取成功");
            mHandler.post(() -> {
                //开始倒计时
                mCountDownTimer = new CountDownTimer(60 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        long result = millisUntilFinished / 1000;
                        mCountDown.postValue(result);
                    }

                    @Override
                    public void onFinish() {
                        mCountDown.postValue(0L);
                    }
                };
                mCountDownTimer.start();
            });
        }).start();
    }

    public void changeColor() {
        mColorType.postValue(mColorType.getValue() != null && 1 == mColorType.getValue() ? 0 : 1);
    }

    public MutableLiveData<Account> getAccount() {
        return mAccount;
    }

    public MutableLiveData<Long> getCountDown() {
        return mCountDown;
    }

    public MutableLiveData<Integer> getColorType() {
        return mColorType;
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        super.onCreate(owner);
        Log.i("TAG", "=============onCreate=============");
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        super.onStop(owner);
        Log.i("TAG", "=============onStop=============");
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        super.onDestroy(owner);
        Log.i("TAG", "=============onDestroy=============");
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        super.onResume(owner);
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        super.onPause(owner);
        Log.i("TAG", "=============onPause=============");
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i("TAG", "=============onCleared=============");
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }
}
