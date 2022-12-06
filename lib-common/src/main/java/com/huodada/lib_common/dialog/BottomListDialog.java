package com.huodada.lib_common.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.huodada.lib_common.R;
import com.huodada.lib_common.databinding.BottomListDialogLayoutBinding;

import java.util.List;

/**
 * 底部弹出列表选择框
 * created by yhw
 * date 2022/12/6
 */
public class BottomListDialog {
    private BottomSheetDialog mDialog;
    private BottomListDialogLayoutBinding mDataBinding;
    private OnConfirmSelectListener mOnConfirmSelectListener;
    private View.OnClickListener mOnCancelClickListener;
    private List<String> mList;

    public BottomListDialog(Context context) {
        init(context);
    }

    private void init(Context context) {
        mDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.bottom_list_dialog_layout, null, false);
        mDataBinding.tvCancel.setOnClickListener(view -> {
            dismiss();
            if (mOnCancelClickListener != null) {
                mOnCancelClickListener.onClick(view);
            }
        });
        mDataBinding.tvConfirm.setOnClickListener(view -> {
            dismiss();
            if (mOnConfirmSelectListener != null) {
                mOnConfirmSelectListener.select(mDataBinding.wheelView.getCurrentItem(),
                        mList.get(mDataBinding.wheelView.getCurrentItem()));
            }
        });

        mDialog = new BottomSheetDialog(context);
        mDialog.setContentView(mDataBinding.getRoot());
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.getBehavior().setDraggable(false);

        mDataBinding.wheelView.setCyclic(false);
        mDataBinding.wheelView.setTextSize(16);
        mDataBinding.wheelView.setLineSpacingMultiplier(2.8f);
        mDataBinding.wheelView.setDividerColor(context.getResources().getColor(com.huodada.lib_src.R.color.main_color));
        mDataBinding.wheelView.setDividerWidth(2);
    }

    /**
     * 设置列表数据
     *
     * @param list 列表数据
     */
    public BottomListDialog setItems(List<String> list) {
        this.mList = list;
        mDataBinding.wheelView.setAdapter(new ArrayWheelAdapter<>(list));
        return this;
    }

    /**
     * 设置当前选中项
     */
    public BottomListDialog setCurrentItem(int position) {
        mDataBinding.wheelView.setCurrentItem(position);
        return this;
    }

    public BottomListDialog setTitle(String title) {
        mDataBinding.tvTitle.setText(title);
        return this;
    }

    public BottomListDialog setTitle(int titleId) {
        mDataBinding.tvTitle.setText(titleId);
        return this;
    }

    public BottomListDialog setCancelText(int textId) {
        mDataBinding.tvCancel.setText(textId);
        return this;
    }

    public BottomListDialog setCancelText(String text) {
        mDataBinding.tvCancel.setText(text);
        return this;
    }

    public BottomListDialog setConfirmText(int textId) {
        mDataBinding.tvConfirm.setText(textId);
        return this;
    }

    public BottomListDialog setConfirmText(String text) {
        mDataBinding.tvConfirm.setText(text);
        return this;
    }

    public BottomListDialog setOnCancelClickListener(View.OnClickListener cancelClickListener) {
        this.mOnCancelClickListener = cancelClickListener;
        return this;
    }

    public BottomListDialog setOnConfirmSelectListener(OnConfirmSelectListener confirmSelectListener) {
        this.mOnConfirmSelectListener = confirmSelectListener;
        return this;
    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

    public interface OnConfirmSelectListener {
        void select(int position, String name);
    }
}
