package com.app;

import androidx.lifecycle.ViewModelProvider;

import com.app.bean.Book;
import com.app.bean.User;
import com.app.databinding.ActivityDataBindingDemoBinding;
import com.app.mvvm.DemoViewModel;
import com.lib_common.base.BaseDataBindingActivity;

public class DataBindingDemoActivity extends BaseDataBindingActivity<ActivityDataBindingDemoBinding> {
    private final Book mBook = new Book();
    private final User mUser = new User();
    private DemoViewModel mDemoViewModel;

    @Override
    protected void onViewEvent() {
        mDataBinding.btnOk.setOnClickListener(v -> {
//            mUser.setName("刘备");

//            mBook.getBookName().set("红楼梦");


            mDemoViewModel.getData(this);
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


        mDemoViewModel = new ViewModelProvider(this).get(DemoViewModel.class);
        mDataBinding.setViewModel(mDemoViewModel);
    }
}