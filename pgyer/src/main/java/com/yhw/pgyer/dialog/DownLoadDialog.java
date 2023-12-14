package com.yhw.pgyer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LifecycleOwner;

import com.blankj.utilcode.util.EncryptUtils;
import com.hjq.toast.ToastUtils;
import com.lib_common.http.HttpListener;
import com.yhw.pgyer.R;
import com.yhw.pgyer.bean.App;
import com.yhw.pgyer.http.HttpRequest;

import java.io.File;

import io.reactivex.disposables.Disposable;
import rxhttp.wrapper.entity.Progress;

/**
 * 下载弹框
 * created by yhw
 * date 2023/12/14
 */
public class DownLoadDialog {
    private final Dialog mDialog;
    private final Context mContext;
    private final ProgressBar mProgressBar;
    private final TextView mTvTitle;
    private Disposable mDisposable;

    public DownLoadDialog(Context context) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_down_load_layout, null);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mTvTitle = view.findViewById(R.id.tv_title);
        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> {
            if (mDisposable != null) {
                mDisposable.dispose();
            }
            dismiss();
            ToastUtils.show("下载已取消");
        });
        mDialog = new AlertDialog.Builder(context)
                .setTitle("下载APK")
                .setView(view)
                .setCancelable(false)
                .create();
    }

    public void down(App.AppInfo appInfo) {
        final String url = HttpRequest.installApp(appInfo.getBuildKey());
        String desPath = mContext.getExternalCacheDir().getPath() + "/" + EncryptUtils.encryptMD5ToString(url) + ".apk";
        Log.i("TAG", "desPath====> " + desPath);
        File file = new File(desPath);
        Log.i("TAG", "desPath size====> " + file.length());
        Log.i("TAG", "appinfo size====> " + appInfo.getBuildFileSize());
        if (appInfo.getBuildFileSize() != null && file.exists() && file.length() == Long.parseLong(appInfo.getBuildFileSize())) {
            dismiss();
            installApk(desPath);
            return;
        }
        mDisposable = HttpRequest.downFile((LifecycleOwner) mContext, url, desPath, new HttpListener<String>() {
            @Override
            public void onSuccess(String s) {
                ToastUtils.show("下载成功，开始安装...");
                dismiss();
                installApk(desPath);
            }

            @Override
            public void onMainProgress(Progress progress) {
                HttpListener.super.onMainProgress(progress);
                mProgressBar.setProgress(progress.getProgress());
                mTvTitle.setText("下载中..." + progress.getProgress() + "%");
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                HttpListener.super.onFail(errorCode, errorMsg);
                dismiss();
                ToastUtils.show("下载发生错误，启用浏览器下载");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                mContext.startActivity(intent);
            }
        });
    }

    private void installApk(String desPath) {
        File apkFile = new File(desPath);
        if (!apkFile.exists()) {
            return;
        }

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri contentUri = FileProvider.getUriForFile(mContext, mContext.getPackageName(), apkFile);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                intent.setDataAndType(Uri.parse("file://" + apkFile), "application/vnd.android.package-archive");
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.show("安装发生错误");
        }
    }

    public DownLoadDialog show() {
        mDialog.show();
        return this;
    }

    public void dismiss() {
        mDialog.dismiss();
    }
}
