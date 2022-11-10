package com.huodada.lib_common.base;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.huodada.lib_common.R;
import com.huodada.lib_common.databinding.ActivityWebBinding;
import com.huodada.lib_common.router.RouterPath;
import com.just.agentweb.AgentWeb;

/**
 * 浏览器
 */
@Route(path = RouterPath.WEB_ACTIVITY)
public class WebActivity extends BaseDataBindingActivity<ActivityWebBinding> {
    @Autowired
    String url; //地址
    private AgentWeb mAgentWeb;

    @Override
    protected void onViewEvent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        super.initView();
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mDataBinding.rootLayout, new ConstraintLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url);
    }

    @Override
    public void onBackPressed() {
        if (mAgentWeb.back()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAgentWeb != null) {
            mAgentWeb.clearWebCache();
            mAgentWeb.destroy();
            mAgentWeb = null;
        }
    }
}