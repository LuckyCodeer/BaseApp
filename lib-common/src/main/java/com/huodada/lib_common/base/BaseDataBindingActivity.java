package com.huodada.lib_common.base;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.huodada.lib_common.R;

/**
 * DataBinding Activity
 * DB类 需要build后自动生成，名字和布局文件名字有关
 * created by yhw
 * date 2022/11/9
 */
public abstract class BaseDataBindingActivity<DB extends ViewDataBinding> extends BaseActivity {
    protected DB mDataBinding; //DataBinding对象，可直接进行布局ID获取

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
        initView();
        onViewEvent();
        initSoftKeyboard();
    }

    @Override
    protected boolean isDataBinding() {
        return true;
    }
}
