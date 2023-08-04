package com.lib_common.base.mvvm;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.lib_common.R;
import com.lib_common.base.BaseActivity;

/**
 * DataBinding Activity
 * DB类 需要build后自动生成，名字和布局文件名字有关
 * created by yhw
 * date 2022/11/9
 */
public abstract class BaseDataBindingActivity<DB extends ViewDataBinding, VM extends BaseViewModel> extends BaseActivity {
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
            mViewModel = new ViewModelProvider(this).get(getViewModel());
            getLifecycle().addObserver(mViewModel); //注册ViewModel生命周期
            if (getVariableId() != 0) {
                mDataBinding.setVariable(getVariableId(), mViewModel);
            }
        }
        initView();
        registerLiveData();
        onViewEvent();
        initSoftKeyboard();
    }

    /**
     * 注册LiveData数据改变监听
     */
    private void registerLiveData() {
        if (mViewModel != null) {
            mViewModel.getShowLoading().observe(this, aBoolean -> {
                if (aBoolean) {
                    showLoading();
                } else {
                    dismissLoading();
                }
            });
        }
    }

    @Override
    protected boolean isDataBinding() {
        return true;
    }

    /**
     * 获取ViewModel
     */
    protected abstract Class<VM> getViewModel();

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
