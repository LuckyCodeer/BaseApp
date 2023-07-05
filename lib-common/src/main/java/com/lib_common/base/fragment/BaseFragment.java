package com.lib_common.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lib_common.dialog.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

/**
 * created by yhw
 * date 2022/11/10
 */
public abstract class BaseFragment extends Fragment {
    protected View mRootView;
    protected LoadingDialog mLoadingDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!isDataBinding()) {
            mRootView = inflater.inflate(getLayoutId(), container);
            initView(mRootView);
            onViewEvent();
        }
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this);
        }
        return mRootView;
    }

    /**
     * 初始化控件
     */
    protected void initView(View rootView) {

    }

    /**
     * View 事件处理方法，如点击，滑动，放大等
     */
    protected abstract void onViewEvent();

    /**
     * 布局文件
     */
    protected abstract int getLayoutId();

    /**
     * 是否为 DataBinding
     */
    protected boolean isDataBinding() {
        return false;
    }

    /**
     * 是否注册EventBus
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

    /**
     * 显示加载框
     */
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(getContext());
        }
        mLoadingDialog.showDialog();
    }

    /**
     * 隐藏加载框
     */
    public void dismissLoading(){
        if (mLoadingDialog == null){
            return;
        }
        mLoadingDialog.dismissDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }
}
