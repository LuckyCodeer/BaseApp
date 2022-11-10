package com.huodada.lib_common.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 基类Activity 其它Activity继承此Activity
 * 推荐继承 {@link BaseDataBindingActivity}
 * created by yhw
 * date 2022/11/9
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isDataBinding()) {
            setContentView(getLayoutId());
            initView();
            onViewEvent();
        }
    }

    /**
     * 初始化控件
     */
    protected void initView() {

    }

    /**
     * View 事件处理方法
     */
    protected abstract void onViewEvent();

    /**
     * 布局文件
     */
    protected abstract int getLayoutId();

    protected boolean isDataBinding() {
        return false;
    }
}
