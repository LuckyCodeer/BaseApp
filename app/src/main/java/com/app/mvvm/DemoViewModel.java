package com.app.mvvm;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.app.bean.Account;
import com.lib_common.base.mvvm.BaseViewModel;

/**
 * created by yhw
 * date 2023/8/3
 */
public class DemoViewModel extends BaseViewModel {
    private final MutableLiveData<Account> mAccount = new MutableLiveData<>();

    public void getData() {
        showLoading();
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Account account = new Account("账户名" + Math.random());
            mAccount.postValue(account);
            dismissLoading();
        }).start();
    }

    public MutableLiveData<Account> getAccount() {
        return mAccount;
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
}
