package com.app.mvvm;

import android.os.AsyncTask;
import android.util.Log;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.app.BR;
import com.app.R;
import com.app.bean.Book;
import com.app.bean.User;
import com.app.databinding.ActivityDataBindingDemoBinding;
import com.app.workmanager.MyWorker;
import com.lib_common.base.mvvm.BaseMvvmActivity;

import java.util.concurrent.TimeUnit;

public class MvvmDemoActivity extends BaseMvvmActivity<ActivityDataBindingDemoBinding, DemoViewModel> {
    private final Book mBook = new Book();
    private final User mUser = new User();

    @Override
    protected void onViewEvent() {
        mDataBinding.btnOk.setOnClickListener(v -> {
//            mUser.setName("刘备");

//            mBook.getBookName().set("红楼梦");

//            mViewModel.getData();
        });
    }

    @Override
    protected void observeDataChange() {
        super.observeDataChange();
        mViewModel.getCountDown().observe(this, aLong -> {
            Log.i("TAG", "====================" + aLong);
            mDataBinding.btnVerify.setEnabled(aLong <= 0);
            if (aLong > 0) {
                mDataBinding.btnVerify.setText(aLong + "s后重新获取");
            } else {
                mDataBinding.btnVerify.setText("重新获取");
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data_binding_demo;
    }

    @Override
    protected void initView() {
        super.initView();
//        mUser.setName("曹操");
//        mUser.setGender("男");
//        mDataBinding.setUser(mUser);

//        mBook.setBookName(new ObservableField<>("西游记"));
//        mDataBinding.setBook(mBook);

        WorkManager.getInstance(this)
                .enqueue(new PeriodicWorkRequest.Builder(MyWorker.class, 10L, TimeUnit.SECONDS).build());
//                .beginWith(new OneTimeWorkRequest.Builder(MyWorker.class).build())
//                .enqueue();
    }

    @Override
    protected int getVariableId() {
        return BR.viewModel;
    }

}