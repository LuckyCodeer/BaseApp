package com.yhw.pgyer;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ClipboardUtils;
import com.hjq.toast.ToastUtils;
import com.lib_common.base.mvvm.BaseMvvmActivity;
import com.lib_common.base.mvvm.BaseViewModel;
import com.lib_common.dialog.BottomActionDialog;
import com.lib_common.dialog.BottomListDialog;
import com.lib_common.dialog.CommonAlertDialog;
import com.lib_common.http.HttpListener;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.tencent.mmkv.MMKV;
import com.yhw.pgyer.adapter.AppListAdapter;
import com.yhw.pgyer.bean.App;
import com.yhw.pgyer.databinding.ActivityAppListBinding;
import com.yhw.pgyer.http.HttpRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * created by yhw
 * date 2023/9/21
 */
public class AppListActivity extends BaseMvvmActivity<ActivityAppListBinding, BaseViewModel> {
    private int page = 1;
    private AppListAdapter mAdapter;
    private CommonAlertDialog mAppUpgradeDialog;

    @Override
    protected void initView() {
        super.initView();
        hideActionBarBack();
        mActionBar.setCenterTextSize(16);
        setTitle(getString(R.string.app_name) + "(" + AppUtils.getAppVersionName() + ")");
        mActionBar.setRightText("切换", view -> {
            showLoading();
            HttpRequest.getMyAppList(this, 1, new HttpListener<App>() {
                @Override
                public void onSuccess(App app) {
                    dismissLoading();
                    showMyAppListDialog(app);
                }

                @Override
                public void onFail(int errorCode, String errorMsg) {
                    HttpListener.super.onFail(errorCode, errorMsg);
                    dismissLoading();
                    ToastUtils.show(errorMsg);
                    showMyAppListDialog(null);
                }
            });
        });
        Constants.APP_KEY = MMKV.defaultMMKV().getString(Constants.BUILD_APP_KEY, Constants.DRIVER_APP_KEY);
        mDataBinding.listLayout.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        getAppList();
    }

    private void showMyAppListDialog(App app) {
        if (app != null) {
            List<App.AppInfo> appList = app.getList();
            if (appList != null && appList.size() != 0) {
                List<String> items = new ArrayList<>();
                List<App.AppInfo> newList = new ArrayList<>();
                for (App.AppInfo appInfo : appList) {
                    //排除IOS及2022年之前的应用
                    int year = 2022;
                    try {
                        String createTime = appInfo.getBuildCreated();
                        if (!TextUtils.isEmpty(createTime)) {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                            Date date = format.parse(createTime);
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date);
                            year = calendar.get(Calendar.YEAR);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if ("2".equals(appInfo.getBuildType()) && year >= 2022) {
                        items.add(appInfo.getBuildName());
                        newList.add(appInfo);
                    }
                }
                showMyAppListNetDialog(items, newList);
                return;
            }
        }
        showMyAppListDefDialog();
    }

    private void showMyAppListNetDialog(List<String> items, List<App.AppInfo> appInfos) {
        BottomListDialog dialog = new BottomListDialog(this);
        dialog.setTitle("切换应用");
        dialog.setItems(items);
        dialog.setOnConfirmSelectListener((position, name) -> {
            ToastUtils.show("已切换到" + name);
            Constants.APP_KEY = appInfos.get(position).getAppKey();
            Constants.INSTALL_PASSWORD = appInfos.get(position).getBuildPassword();
            MMKV.defaultMMKV().putString(Constants.BUILD_APP_KEY, Constants.APP_KEY);
            mDataBinding.listLayout.autoRefresh();
        });
        dialog.show();
    }

    private void showMyAppListDefDialog() {
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
            MMKV.defaultMMKV().putString(Constants.BUILD_APP_KEY, Constants.APP_KEY);
            mDataBinding.listLayout.autoRefresh();
        });
        dialog.show();
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
                    Log.i("AppListActivity bb", "getBuildName: " + app.getList().get(0).getBuildName());
                    MMKV.defaultMMKV().putInt(Constants.APP_KEY,
                            Integer.parseInt(app.getList().get(0).getBuildBuildVersion()));
                }
                if (page == 1) {
                    mDataBinding.listLayout.smoothScrollToPosition(0);
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

    @Override
    protected void onResume() {
        super.onResume();
        checkAppUpgrade();
    }

    private void checkAppUpgrade() {
        if (mAppUpgradeDialog != null && mAppUpgradeDialog.isShowing()) {
            return;
        }
        HttpRequest.checkAppUpgrade(this, new HttpListener<App.AppInfo>() {
            @Override
            public void onSuccess(App.AppInfo app) {
                if (app == null || TextUtils.isEmpty(app.getBuildVersion()) || "0".equals(app.getBuildVersion())
                        || "0".equals(app.getBuildBuildVersion())) {
                    return;
                }
                if (Integer.parseInt(app.getBuildVersion().replace(".", "")) > AppUtils.getAppVersionCode()) {
                    mAppUpgradeDialog = new CommonAlertDialog(AppListActivity.this)
                            .setTitle("发现新版本")
                            .setLeftMessage("更新内容：\n" + (TextUtils.isEmpty(app.getBuildUpdateDescription()) ? "无" : app.getBuildUpdateDescription())
                                    + "\n\n请切换到[Android蒲公英安装]，下载最新版本。")
                            .hideCancel();
                    mAppUpgradeDialog.show();
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                HttpListener.super.onFail(errorCode, errorMsg);
            }
        });
    }
}
