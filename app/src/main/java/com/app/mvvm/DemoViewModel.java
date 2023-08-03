package com.app.mvvm;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.bean.Account;
import com.lib_common.dialog.LoadingDialog;

/**
 * created by yhw
 * date 2023/8/3
 */
public class DemoViewModel extends ViewModel {
    private final MutableLiveData<Account> mAccount = new MutableLiveData<>();

    public void getData(Context context) {
        LoadingDialog loadingDialog = new LoadingDialog(context);
        loadingDialog.showDialog();
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Account account = new Account("账户名" + Math.random());
            mAccount.postValue(account);
            loadingDialog.dismissDialog();
        }).start();
    }

    public MutableLiveData<Account> getAccount() {
        return mAccount;
    }

}
