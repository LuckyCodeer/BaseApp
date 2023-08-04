package com.app.mvvm;

import android.util.Log;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.app.BR;
import com.app.R;
import com.app.bean.Book;
import com.app.bean.User;
import com.app.databinding.ActivityDataBindingDemoBinding;
import com.app.mvvm.DemoViewModel;
import com.lib_common.base.mvvm.BaseDataBindingActivity;
import com.lib_common.base.mvvm.BaseViewModel;

public class DataBindingDemoActivity extends BaseDataBindingActivity<ActivityDataBindingDemoBinding, DemoViewModel> {
    private final Book mBook = new Book();
    private final User mUser = new User();

    @Override
    protected void onViewEvent() {
        mDataBinding.btnOk.setOnClickListener(v -> {
//            mUser.setName("刘备");

//            mBook.getBookName().set("红楼梦");


            mViewModel.getData();
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

    }

    @Override
    protected Class<DemoViewModel> getViewModel() {
        return DemoViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return BR.viewModel;
    }
}