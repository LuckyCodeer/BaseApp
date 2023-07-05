package com.lib_common.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * created by yhw
 * date 2022/11/10
 */
public abstract class BaseDataBindingFragment<DB extends ViewDataBinding> extends BaseFragment {
    protected DB mDataBinding; //DataBinding对象，可直接进行布局ID获取

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mRootView = mDataBinding.getRoot();
        initView(mRootView);
        onViewEvent();
        return mRootView;
    }

    @Override
    protected boolean isDataBinding() {
        return true;
    }
}
