package com.lib_common.base.mvvm;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.lib_common.R;
import com.lib_common.base.BaseActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * MVVM基类Activity
 * @param <DB> 需要build后自动生成，名字和布局文件名字有关
 * @param <VM> ViewModel
 * created by yhw
 * date 2022/11/9
 */
public abstract class BaseMvvmActivity<DB extends ViewDataBinding, VM extends BaseViewModel> extends BaseActivity {
    protected DB mDataBinding; //DataBinding对象，可直接进行布局ID获取
    protected VM mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isShowActionBar()) {
            setContentView(R.layout.activity_base_layout);
            mDataBinding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), null, false);
            ((ViewGroup) findViewById(R.id.fl_content)).addView(mDataBinding.getRoot());
            mActionBar = findViewById(R.id.actionbar);
        } else {
            mDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        }
        mDataBinding.setLifecycleOwner(this);
        if (getViewModel() != null) {
            mViewModel = getViewModel();
            getLifecycle().addObserver(mViewModel); //注册ViewModel生命周期
            if (getVariableId() != 0) {
                mDataBinding.setVariable(getVariableId(), mViewModel);
            }
        }
        initView();
        observeDataChange();
        onViewEvent();
        initSoftKeyboard();
    }

    /**
     * 观察数据变化，更新UI
     */
    protected void observeDataChange() {
        if (mViewModel != null) {
            mViewModel.getShowLoading().observe(this, aBoolean -> {
                if (aBoolean) {
                    showLoading();
                } else {
                    dismissLoading();
                }
            });

            mViewModel.getLoadingMsg().observe(this, this::showLoading);
        }
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
    protected void onDestroy() {
        super.onDestroy();
        if (mViewModel != null) {
            getLifecycle().removeObserver(mViewModel);
        }
    }
}
