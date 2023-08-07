package com.lib_common.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.lib_common.base.mvvm.BaseViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * MVVM基类Fragment
 * @param <DB> 需要build后自动生成，名字和布局文件名字有关
 * @param <VM> ViewModel
 * created by yhw
 * date 2022/11/10
 */
public abstract class BaseMvvmFragment<DB extends ViewDataBinding, VM extends BaseViewModel> extends BaseFragment {
    protected DB mDataBinding; //DataBinding对象，可直接进行布局ID获取
    protected VM mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mRootView = mDataBinding.getRoot();
        mDataBinding.setLifecycleOwner(this);
        if (getViewModel() != null) {
            mViewModel = getViewModel();
            getLifecycle().addObserver(mViewModel); //注册ViewModel生命周期
            if (getVariableId() != 0) {
                mDataBinding.setVariable(getVariableId(), mViewModel);
            }
        }
        initView(mRootView);
        onViewEvent();
        return mRootView;
    }

    @Override
    protected boolean isDataBinding() {
        return true;
    }

    /**
     * 获取ViewModel
     */
    protected VM getViewModel() {
        //这里获得到的是类的泛型的类型
        final Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            final Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            Type t = actualTypeArguments[1];
            return new ViewModelProvider(this).get((Class<VM>) t);
        }
        return null;
    }

    /**
     * 获取布局里的variable的name
     */
    protected abstract int getVariableId();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mViewModel != null) {
            getLifecycle().removeObserver(mViewModel);
        }
    }
}
