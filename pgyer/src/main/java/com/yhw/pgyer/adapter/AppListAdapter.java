package com.yhw.pgyer.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.viewholder.DataBindingHolder;
import com.hjq.toast.ToastUtils;
import com.lib_common.adapter.CommonAdapter;
import com.tencent.mmkv.MMKV;
import com.yhw.pgyer.Constants;
import com.yhw.pgyer.R;
import com.yhw.pgyer.bean.App;
import com.yhw.pgyer.databinding.AppListItemLayoutBinding;
import com.yhw.pgyer.dialog.DownLoadDialog;

import java.util.List;

/**
 * created by yhw
 * date 2023/9/21
 */
public class AppListAdapter extends CommonAdapter<App.AppInfo, AppListItemLayoutBinding> {
    private static int lastBuildVersion;

    public AppListAdapter(@NonNull List<? extends App.AppInfo> items) {
        super(items);
    }

    @Override
    public void onBindViewHolder(@NonNull DataBindingHolder<AppListItemLayoutBinding> holder, AppListItemLayoutBinding dataBinding, int position, @Nullable App.AppInfo appInfo) {
        dataBinding.setAppInfo(appInfo);
        String endName = "";
        if (("9.9.8".equals(appInfo.getBuildVersion()) || Integer.parseInt(appInfo.getBuildVersion().replace(".","")) >= 240)
                && !"9.9.9".equals(appInfo.getBuildVersion())
                && ("com.yunxiaobao.tms.driver".equals(appInfo.getBuildIdentifier()) || "com.yunxiaobao.shipper".equals(appInfo.getBuildIdentifier()))) {
            endName = "(融合)";
        }
        dataBinding.tvName.setText("APP名称：" + appInfo.getBuildName() + endName);
        dataBinding.tvSize.setText("大小：" + ConvertUtils.byte2FitMemorySize(Long.parseLong(appInfo.getBuildFileSize())) + "\u3000下载次数：" + appInfo.getBuildDownloadCount());
        dataBinding.tvDownCount.setText("下载次数：" + appInfo.getBuildDownloadCount());
        Log.i("TAG", "getBuildName: " + appInfo.getBuildName() + "lastBuildVersion: " + lastBuildVersion);
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
        if (TextUtils.isEmpty(appInfo.getBuildBuildVersion()) || "0".equals(appInfo.getBuildBuildVersion())) {
            ToastUtils.show("该版本有问题，请选择其他版本下载");
            return;
        }
        /*Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(HttpRequest.installApp(appInfo.getBuildKey())));
        getContext().startActivity(intent);*/
        new DownLoadDialog(getContext())
                .show()
                .down(appInfo);
    }

    public void setVersion() {
        lastBuildVersion = MMKV.defaultMMKV().getInt(Constants.APP_KEY, 0);
    }
}
