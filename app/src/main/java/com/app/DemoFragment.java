package com.app;

import android.view.View;

import com.app.R;
import com.app.databinding.FragmentDemoLayoutBinding;
import com.lib_common.base.fragment.BaseDataBindingFragment;

/**
 * created by yhw
 * date 2022/11/10
 */
public class DemoFragment extends BaseDataBindingFragment<FragmentDemoLayoutBinding> {

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
    }

    @Override
    protected void onViewEvent() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_demo_layout;
    }

}
