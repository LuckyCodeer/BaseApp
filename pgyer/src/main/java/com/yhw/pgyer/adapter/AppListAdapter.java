package com.yhw.pgyer.adapter;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.viewholder.DataBindingHolder;
import com.lib_common.adapter.CommonAdapter;
import com.tencent.mmkv.MMKV;
import com.yhw.pgyer.Constants;
import com.yhw.pgyer.R;
import com.yhw.pgyer.bean.App;
import com.yhw.pgyer.databinding.AppListItemLayoutBinding;
import com.yhw.pgyer.http.HttpRequest;

import java.util.List;

/**
 * created by yhw
 * date 2023/9/21
 */
public class AppListAdapter extends CommonAdapter<App.AppInfo, AppListItemLayoutBinding> {
    private static int appType;
    private static int lastBuildVersion;

    public AppListAdapter(@NonNull List<? extends App.AppInfo> items) {
        super(items);
    }

    @Override
    public void onBindViewHolder(@NonNull DataBindingHolder<AppListItemLayoutBinding> holder, AppListItemLayoutBinding dataBinding, int position, @Nullable App.AppInfo appInfo) {
        dataBinding.setAppInfo(appInfo);
        String endName = "";
        if ("9.9.8".equals(appInfo.getBuildVersion()) || appInfo.getBuildVersion().startsWith("2.")) {
            endName = "(融合)";
        }
        dataBinding.tvName.setText("APP名称：" + appInfo.getBuildName() + endName);
        dataBinding.tvSize.setText("大小：" + ConvertUtils.byte2FitMemorySize(Long.parseLong(appInfo.getBuildFileSize())));
        Log.i("TAG", "appType: " + appType + " ,lastBuildVersion: " + lastBuildVersion);
        if (lastBuildVersion > 0 && Integer.parseInt(appInfo.getBuildBuildVersion()) > lastBuildVersion) {
            dataBinding.ivNew.setVisibility(View.VISIBLE);
        } else {
            dataBinding.ivNew.setVisibility(View.GONE);
        }
        dataBinding.btnInstall.setOnClickListener(view -> {
            install(appInfo);
        });
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.app_list_item_layout;
    }

    private void install(App.AppInfo appInfo) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(HttpRequest.installApp(appInfo.getBuildKey())));
        getContext().startActivity(intent);
    }

    public void setVersion() {
        appType = MMKV.defaultMMKV().getInt(Constants.BUILD_APP_KEY, 0);
        lastBuildVersion = MMKV.defaultMMKV().getInt(appType == 0 ? Constants.DRIVER_BUILD_VERSION_KEY : Constants.SHIPPER_BUILD_VERSION_KEY, 0);
        Log.i("TAG1111", "appType: " + appType + " ,lastBuildVersion: " + lastBuildVersion);
    }
}
