package com.app;

import com.app.databinding.ActivityFormBinding;
import com.lib_common.base.BaseDataBindingActivity;

public class FormActivity extends BaseDataBindingActivity<ActivityFormBinding> {

    @Override
    protected void initView() {
        super.initView();
        setTitle("form表单");
    }

    @Override
    protected void onViewEvent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_form;
    }
}