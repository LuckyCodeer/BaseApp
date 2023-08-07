package com.lib_common.base;

import android.webkit.WebView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib_common.R;
import com.lib_common.base.mvvm.BaseMvvmActivity;
import com.lib_common.base.mvvm.BaseViewModel;
import com.lib_common.databinding.ActivityWebBinding;
import com.lib_common.router.RouterPath;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.MiddlewareWebChromeBase;

/**
 * 浏览器
 */
@Route(path = RouterPath.WEB_ACTIVITY)
public class WebActivity extends BaseMvvmActivity<ActivityWebBinding, BaseViewModel> {
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
        mAgentWeb.getWebCreator().getWebView().setWebChromeClient(new MiddlewareWebChromeBase() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitle(title);
            }
        });
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

    @Override
    protected int getVariableId() {
        return 0;
    }
}