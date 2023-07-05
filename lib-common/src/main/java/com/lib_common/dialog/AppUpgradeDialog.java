package com.lib_common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.lib_common.R;
import com.lib_common.databinding.AppUpgradeLayoutBinding;

/**
 * App升级弹框
 * created by yhw
 * date 2022/12/6
 */
public class AppUpgradeDialog implements DefaultLifecycleObserver {
    private static final String TAG = AppUpgradeDialog.class.getSimpleName();
    private Dialog mDialog;
    private AppUpgradeLayoutBinding mDataBinding;
    private View.OnClickListener mOnConfirmClickListener;
    private View.OnClickListener mOnCancelClickListener;

    public AppUpgradeDialog(Context context) {
        init(context);
    }

    private void init(Context context) {
        addLifecycleObserver((LifecycleOwner) context);
        mDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.app_upgrade_layout, null, false);
        mDataBinding.tvCancel.setOnClickListener(view -> {
            dismiss();
            if (mOnCancelClickListener != null) {
                mOnCancelClickListener.onClick(view);
            }
        });
        mDataBinding.tvConfirm.setOnClickListener(view -> {
            dismiss();
            if (mOnConfirmClickListener != null) {
                mOnConfirmClickListener.onClick(view);
            }
        });

        mDialog = new AlertDialog.Builder(context, R.style.BaseDialogStyle)
                .setView(mDataBinding.getRoot())
                .create();
        mDialog.setCanceledOnTouchOutside(false);
    }

    public AppUpgradeDialog setTitle(String title) {
        mDataBinding.tvTitle.setText(title);
        return this;
    }

    public AppUpgradeDialog setTitle(int titleId) {
        mDataBinding.tvTitle.setText(titleId);
        return this;
    }

    public AppUpgradeDialog setMessage(String message) {
        mDataBinding.tvMsg.setText(message);
        return this;
    }

    public AppUpgradeDialog setMessage(int messageId) {
        mDataBinding.tvMsg.setText(messageId);
        return this;
    }

    /**
     * 设置富文本消息
     */
    public AppUpgradeDialog setRichTextMessage(SpannableString spannableString) {
        mDataBinding.tvMsg.setGravity(Gravity.START);
        mDataBinding.tvMsg.setText(spannableString, TextView.BufferType.SPANNABLE);
        mDataBinding.tvMsg.setMovementMethod(LinkMovementMethod.getInstance());
        return this;
    }

    public AppUpgradeDialog setLeftMessage(String message) {
        mDataBinding.tvMsg.setGravity(Gravity.START);
        return setMessage(message);
    }

    public AppUpgradeDialog setLeftMessage(int messageId) {
        mDataBinding.tvMsg.setGravity(Gravity.START);
        return setMessage(messageId);
    }

    public AppUpgradeDialog setCancelText(int textId) {
        mDataBinding.tvCancel.setText(textId);
        return this;
    }

    public AppUpgradeDialog setCancelText(String text) {
        mDataBinding.tvCancel.setText(text);
        return this;
    }

    public AppUpgradeDialog setConfirmText(int textId) {
        mDataBinding.tvConfirm.setText(textId);
        return this;
    }

    public AppUpgradeDialog setConfirmText(String text) {
        mDataBinding.tvConfirm.setText(text);
        return this;
    }

    /**
     * 隐藏取消按钮
     */
    public AppUpgradeDialog hideCancel() {
        mDataBinding.viewLine.setVisibility(View.GONE);
        mDataBinding.tvCancel.setVisibility(View.GONE);
        return this;
    }

    public AppUpgradeDialog setOnCancelClickListener(View.OnClickListener cancelClickListener) {
        this.mOnCancelClickListener = cancelClickListener;
        return this;
    }

    public AppUpgradeDialog setOnConfirmClickListener(View.OnClickListener confirmClickListener) {
        this.mOnConfirmClickListener = confirmClickListener;
        return this;
    }

    /**
     * 提示信息-静态调用
     * 适用于仅显示一个确认按钮的提示信息
     */
    public static void showDialog(Context context, String msg) {
        new AppUpgradeDialog(context).setMessage(msg).hideCancel().show();
    }

    /**
     * 检测更新
     */
    public void checkUpgrade() {
        mDataBinding.tvVersion.setText("发现新版本 v1.0.1");
        setMessage("1、修复了一些bug\n2、优化了一些细节");
        show();
    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

    /**
     * 注册生命周期
     */
    public void addLifecycleObserver(LifecycleOwner owner) {
        if (owner != null) {
            owner.getLifecycle().addObserver(this);
        }
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        Log.i(TAG, "onCreate==> " + owner);
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        Log.i(TAG, "onDestroy==> " + owner);
        owner.getLifecycle().removeObserver(this);
    }
}
