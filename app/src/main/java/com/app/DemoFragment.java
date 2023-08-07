package com.app;

import android.view.View;

import com.app.databinding.FragmentDemoLayoutBinding;
import com.lib_common.base.fragment.BaseMvvmFragment;
import com.lib_common.base.mvvm.BaseViewModel;

/**
 * created by yhw
 * date 2022/11/10
 */
public class DemoFragment extends BaseMvvmFragment<FragmentDemoLayoutBinding, BaseViewModel> {

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

    @Override
    protected int getVariableId() {
        return 0;
    }
}
