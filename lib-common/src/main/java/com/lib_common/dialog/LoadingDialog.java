package com.lib_common.dialog;


import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.lib_common.R;


/**
 * 加载框
 */
public class LoadingDialog {
    private final Context mContext;
    private Dialog mLoadingDialog;
    private TextView mTvMsg;

    public LoadingDialog(Context context) {
        mContext = context;
        init();
    }

    private void init() {
        mLoadingDialog = new Dialog(mContext, R.style.loading_dialog);
        mLoadingDialog.setContentView(R.layout.loading_dialog_layout);
        mTvMsg = mLoadingDialog.findViewById(R.id.tv_show_text);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setCancelable(false);
    }

    public void showDialog(String msg, boolean cancelable) {
        if (!TextUtils.isEmpty(msg)) {
            mTvMsg.setText(msg);
        }
        mLoadingDialog.setCancelable(cancelable);
        mLoadingDialog.show();
    }

    public void showDialog(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            mTvMsg.setText(msg);
        }
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.show();
    }

    public void showDialog() {
        showDialog(null);
    }

    public void dismissDialog() {
        if (mLoadingDialog == null || !mLoadingDialog.isShowing()){
            return;
        }
        mLoadingDialog.dismiss();
    }

    public boolean isShowing() {
        if (mLoadingDialog == null) {
            return false;
        }
        return mLoadingDialog.isShowing();
    }
}
    