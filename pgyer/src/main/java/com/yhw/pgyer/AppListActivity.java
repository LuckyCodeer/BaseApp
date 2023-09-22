package com.yhw.pgyer;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.hjq.toast.ToastUtils;
import com.lib_common.base.mvvm.BaseMvvmActivity;
import com.lib_common.base.mvvm.BaseViewModel;
import com.lib_common.http.HttpListener;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.tencent.mmkv.MMKV;
import com.yhw.pgyer.adapter.AppListAdapter;
import com.yhw.pgyer.bean.App;
import com.yhw.pgyer.databinding.ActivityAppListBinding;
import com.yhw.pgyer.http.HttpRequest;

/**
 * created by yhw
 * date 2023/9/21
 */
public class AppListActivity extends BaseMvvmActivity<ActivityAppListBinding, BaseViewModel> {
    private int page = 1;
    private int appType;

    @Override
    protected void initView() {
        super.initView();
        hideActionBarBack();
        setTitle(R.string.app_name);
        mActionBar.setRightText("切换", view -> {
            if (Constants.SHIPPER_APP_KEY.equals(Constants.APP_KEY)) {
                Constants.APP_KEY = Constants.DRIVER_APP_KEY;
                ToastUtils.show("已切换到司机APP");
                appType = 0;
            } else {
                Constants.APP_KEY = Constants.SHIPPER_APP_KEY;
                ToastUtils.show("已切换到货主APP");
                appType = 1;
            }
            MMKV.defaultMMKV().putInt(Constants.BUILD_APP_KEY, appType);
            mDataBinding.listLayout.autoRefresh();
        });
        appType = MMKV.defaultMMKV().getInt(Constants.BUILD_APP_KEY, 0);
        if (appType == 1) {
            Constants.APP_KEY = Constants.SHIPPER_APP_KEY;
        } else {
            Constants.APP_KEY = Constants.DRIVER_APP_KEY;
        }
        mDataBinding.listLayout.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        getAppList();
    }

    @Override
    protected void onViewEvent() {
        mDataBinding.listLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getAppList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getAppList();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_app_list;
    }

    @Override
    protected int getVariableId() {
        return 0;
    }

    private void getAppList() {
        showLoading();
        HttpRequest.getAppList(this, page, new HttpListener<App>() {
            @Override
            public void onSuccess(App app) {
                dismissLoading();
                if (app == null) {
                    return;
                }
                AppListAdapter adapter = new AppListAdapter(app.getList());
                adapter.setVersion();
                mDataBinding.listLayout.setAdapter(adapter, page, app.getPageCount());
                if (app.getList().size() > 0) {
                    if (page == 1) {
                        Log.i("AppListActivity bb", "appType: " + appType);
                        MMKV.defaultMMKV().putInt(appType == 0 ? Constants.DRIVER_BUILD_VERSION_KEY : Constants.SHIPPER_BUILD_VERSION_KEY,
                                Integer.parseInt(app.getList().get(0).getBuildBuildVersion()));
                    }
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoading();
                mDataBinding.listLayout.finishRefresh();
                mDataBinding.listLayout.finishLoadMore();
                ToastUtils.show(errorMsg);
            }
        });
    }

}
