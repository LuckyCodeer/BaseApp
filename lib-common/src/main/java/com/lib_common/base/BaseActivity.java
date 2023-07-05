package com.lib_common.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib_common.R;
import com.lib_common.dialog.LoadingDialog;
import com.lib_common.view.layout.ActionBar;

import org.greenrobot.eventbus.EventBus;

/**
 * 基类Activity 其它Activity继承此Activity
 * 推荐继承 {@link BaseDataBindingActivity}
 * created by yhw
 * date 2022/11/9
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected ActionBar mActionBar;
    protected LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        if (!isDataBinding()) {
            //每个界面添加actionbar
            if (isShowActionBar()) {
                setContentView(R.layout.activity_base_layout);
                ((ViewGroup) findViewById(R.id.fl_content)).addView(getLayoutInflater().inflate(getLayoutId(), null));
                mActionBar = findViewById(R.id.actionbar);
            } else {
                setContentView(getLayoutId());
            }
            initView();
            onViewEvent();
            initSoftKeyboard();
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

    /**
     * 是否显示actionbar
     */
    protected boolean isShowActionBar() {
        return true;
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        if (mActionBar != null) {
            mActionBar.setCenterText(getString(titleId));
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (mActionBar != null) {
            mActionBar.setCenterText(title);
        }
    }

    /**
     * 隐藏ActionBar返回按钮
     */
    public void hideActionBarBack() {
        mActionBar.getLeftView().setVisibility(View.INVISIBLE);
    }

    /**
     * 显示加载框
     */
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
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

    /**
     * 初始化软键盘
     */
    protected void initSoftKeyboard() {
        // 点击外部隐藏软键盘，提升用户体验
        getContentView().setOnClickListener(v -> {
            // 隐藏软键，避免内存泄漏
            hideKeyboard(getCurrentFocus());
        });
    }

    @Override
    public void finish() {
        super.finish();
        // 隐藏软键，避免内存泄漏
        hideKeyboard(getCurrentFocus());
    }

    /**
     * 隐藏软键盘
     */
    protected void hideKeyboard(View view) {
        if (view == null) {
            return;
        }
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager == null) {
            return;
        }
        manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 和 setContentView 对应的方法
     */
    public ViewGroup getContentView() {
        return findViewById(Window.ID_ANDROID_CONTENT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        dismissLoading();
    }
}
