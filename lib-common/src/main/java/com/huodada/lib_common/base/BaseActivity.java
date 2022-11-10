package com.huodada.lib_common.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;

import org.greenrobot.eventbus.EventBus;

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
        ARouter.getInstance().inject(this);
        if (!isDataBinding()) {
            setContentView(getLayoutId());
            initView();
            onViewEvent();
        }
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 初始化控件
     */
    protected void initView() {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }
}
