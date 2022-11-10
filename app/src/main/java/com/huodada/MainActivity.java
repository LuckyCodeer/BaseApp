package com.huodada;

import android.content.Intent;

import com.huodada.databinding.ActivityMainBinding;
import com.huodada.lib_common.base.BaseDataBindingActivity;
import com.huodada.lib_common.base.WebActivity;

/**
 * 主界面
 */
public class MainActivity extends BaseDataBindingActivity<ActivityMainBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewEvent() {
        mDataBinding.btnDemo.setOnClickListener(view -> {
            startActivity(new Intent(this, DemoActivity.class));
        });

        mDataBinding.btnBrowser.setOnClickListener(view -> {
            startActivity(new Intent(this, WebActivity.class));
        });
    }
}