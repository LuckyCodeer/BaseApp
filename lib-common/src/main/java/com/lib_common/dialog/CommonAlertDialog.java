package com.lib_common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import com.lib_common.R;
import com.lib_common.databinding.CommonAlertLayoutBinding;

/**
 * 对话框
 * created by yhw
 * date 2022/12/6
 */
public class CommonAlertDialog {
    private Dialog mDialog;
    private CommonAlertLayoutBinding mDataBinding;
    private View.OnClickListener mOnConfirmClickListener;
    private View.OnClickListener mOnCancelClickListener;

    public CommonAlertDialog(Context context) {
        init(context);
    }

    private void init(Context context) {
        mDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.common_alert_layout, null, false);
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

    public CommonAlertDialog setTitle(String title) {
        mDataBinding.tvTitle.setText(title);
        return this;
    }

    public CommonAlertDialog setTitle(int titleId) {
        mDataBinding.tvTitle.setText(titleId);
        return this;
    }

    public CommonAlertDialog setMessage(String message) {
        mDataBinding.tvMsg.setText(message);
        return this;
    }

    public CommonAlertDialog setMessage(int messageId) {
        mDataBinding.tvMsg.setText(messageId);
        return this;
    }

    /**
     * 设置富文本消息
     */
    public CommonAlertDialog setRichTextMessage(SpannableString spannableString) {
        mDataBinding.tvMsg.setGravity(Gravity.START);
        mDataBinding.tvMsg.setText(spannableString, TextView.BufferType.SPANNABLE);
        mDataBinding.tvMsg.setMovementMethod(LinkMovementMethod.getInstance());
        return this;
    }

    public CommonAlertDialog setLeftMessage(String message) {
        mDataBinding.tvMsg.setGravity(Gravity.START);
        return setMessage(message);
    }

    public CommonAlertDialog setLeftMessage(int messageId) {
        mDataBinding.tvMsg.setGravity(Gravity.START);
        return setMessage(messageId);
    }

    public CommonAlertDialog setCancelText(int textId) {
        mDataBinding.tvCancel.setText(textId);
        return this;
    }

    public CommonAlertDialog setCancelText(String text) {
        mDataBinding.tvCancel.setText(text);
        return this;
    }

    public CommonAlertDialog setConfirmText(int textId) {
        mDataBinding.tvConfirm.setText(textId);
        return this;
    }

    public CommonAlertDialog setConfirmText(String text) {
        mDataBinding.tvConfirm.setText(text);
        return this;
    }

    /**
     * 隐藏取消按钮
     */
    public CommonAlertDialog hideCancel() {
        mDataBinding.viewLine.setVisibility(View.GONE);
        mDataBinding.tvCancel.setVisibility(View.GONE);
        return this;
    }

    public CommonAlertDialog setOnCancelClickListener(View.OnClickListener cancelClickListener) {
        this.mOnCancelClickListener = cancelClickListener;
        return this;
    }

    public CommonAlertDialog setOnConfirmClickListener(View.OnClickListener confirmClickListener) {
        this.mOnConfirmClickListener = confirmClickListener;
        return this;
    }

    /**
     * 提示信息-静态调用
     * 适用于仅显示一个确认按钮的提示信息
     */
    public static void showDialog(Context context, String msg) {
        new CommonAlertDialog(context).setMessage(msg).hideCancel().show();
    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }
}
