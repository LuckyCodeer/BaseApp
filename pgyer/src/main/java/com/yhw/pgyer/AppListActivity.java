package com.yhw.pgyer;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.blankj.utilcode.util.ClipboardUtils;
import com.hjq.toast.ToastUtils;
import com.lib_common.base.mvvm.BaseMvvmActivity;
import com.lib_common.base.mvvm.BaseViewModel;
import com.lib_common.dialog.BottomActionDialog;
import com.lib_common.http.HttpListener;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.tencent.mmkv.MMKV;
import com.yhw.pgyer.adapter.AppListAdapter;
import com.yhw.pgyer.bean.App;
import com.yhw.pgyer.databinding.ActivityAppListBinding;
import com.yhw.pgyer.http.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * created by yhw
 * date 2023/9/21
 */
public class AppListActivity extends BaseMvvmActivity<ActivityAppListBinding, BaseViewModel> {
    private int page = 1;
    private int appType;
    private AppListAdapter mAdapter;

    @Override
    protected void initView() {
        super.initView();
        hideActionBarBack();
        setTitle(R.string.app_name);
        mActionBar.setRightText("切换", view -> {
            List<String> items = new ArrayList<>();
            items.add("司机");
            items.add("货主");
            items.add("PDA");
            items.add("本应用");
            BottomActionDialog dialog = new BottomActionDialog(this);
            dialog.setTitle("切换应用");
            dialog.setItems(items);
            dialog.setOnConfirmSelectListener((position, name) -> {
                ToastUtils.show("已切换到" + name);
                appType = position;
                switch (position) {
                    case 0:
                        Constants.APP_KEY = Constants.DRIVER_APP_KEY;
                        break;
                    case 1:
                        Constants.APP_KEY = Constants.SHIPPER_APP_KEY;
                        break;
                    case 2:
                        Constants.APP_KEY = Constants.PDA_APP_KEY;
                        break;
                    case 3:
                        Constants.APP_KEY = Constants.MYSELF_APP_KEY;
                        break;
                }
                MMKV.defaultMMKV().putInt(Constants.BUILD_APP_KEY, appType);
                mDataBinding.listLayout.autoRefresh();
            });
            dialog.show();
        });
        appType = MMKV.defaultMMKV().getInt(Constants.BUILD_APP_KEY, 0);
        if (appType == 1) {
            Constants.APP_KEY = Constants.SHIPPER_APP_KEY;
        } else if (appType == 2) {
            Constants.APP_KEY = Constants.PDA_APP_KEY;
        } else if (appType == 3) {
            Constants.APP_KEY = Constants.MYSELF_APP_KEY;
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

        mDataBinding.listLayout.setOnItemLongClickListener((itemView, position, item) -> {
            App.AppInfo appInfo = (App.AppInfo) item;
            BottomActionDialog dialog = new BottomActionDialog(this);
            final List<String> items = new ArrayList<>();
            items.add("复制下载链接");
            dialog.setItems(items);
            dialog.show();
            dialog.setOnConfirmSelectListener((i, name) -> {
                ClipboardUtils.copyText(HttpRequest.installApp(appInfo.getBuildKey()));
                ToastUtils.show("复制成功");
            });
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
                mAdapter = new AppListAdapter(app.getList());
                mAdapter.setVersion();
                mDataBinding.listLayout.setAdapter(mAdapter, page, app.getPageCount());
                //记录最后一次查询记录的buildVersion
                if (page == 1 && app.getList() != null && app.getList().size() > 0 && !"0".equals(app.getList().get(0).getBuildBuildVersion())) {
                    Log.i("AppListActivity bb", "appType: " + appType);
                    MMKV.defaultMMKV().putInt(Constants.APP_KEY,
                            Integer.parseInt(app.getList().get(0).getBuildBuildVersion()));
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
