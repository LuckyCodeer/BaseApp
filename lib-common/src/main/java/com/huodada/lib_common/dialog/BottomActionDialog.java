package com.huodada.lib_common.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.chad.library.adapter.base.viewholder.DataBindingHolder;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.huodada.lib_common.R;
import com.huodada.lib_common.adapter.CommonAdapter;
import com.huodada.lib_common.databinding.BottomActionDialogLayoutBinding;
import com.huodada.lib_common.databinding.BottomActionItemLayoutBinding;

import java.util.List;

/**
 * 底部弹出选择框-适用于数据较少的场景，如：选择图片时：拍照、相册选择等
 * created by yhw
 * date 2022/12/7
 */
public class BottomActionDialog {
    private BottomSheetDialog mDialog;
    private BottomActionDialogLayoutBinding mDataBinding;
    private OnConfirmSelectListener mOnConfirmSelectListener;
    private View.OnClickListener mOnCancelClickListener;

    public BottomActionDialog(Context context) {
        init(context);
    }

    private void init(Context context) {
        mDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.bottom_action_dialog_layout, null, false);
        mDataBinding.tvCancel.setOnClickListener(view -> {
            dismiss();
            if (mOnCancelClickListener != null) {
                mOnCancelClickListener.onClick(view);
            }
        });

        mDialog = new BottomSheetDialog(context, R.style.BaseDialogStyle);
        mDialog.setContentView(mDataBinding.getRoot());
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.getBehavior().setDraggable(false);

        mDataBinding.recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
    }

    private static class MyAdapter extends CommonAdapter<String, BottomActionItemLayoutBinding> {

        public MyAdapter(@NonNull List<? extends String> items) {
            super(items);
        }

        @Override
        public void onBindViewHolder(@NonNull DataBindingHolder<BottomActionItemLayoutBinding> holder, BottomActionItemLayoutBinding dataBinding, int position, @Nullable String s) {
            dataBinding.tvContent.setText(s);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.bottom_action_item_layout;
        }
    }

    /**
     * 设置列表数据
     *
     * @param list 列表数据
     */
    public BottomActionDialog setItems(List<String> list) {
        MyAdapter adapter = new MyAdapter(list);
        adapter.setOnItemClickListener((stringBaseQuickAdapter, view, integer) -> {
            dismiss();
            if (mOnConfirmSelectListener != null) {
                mOnConfirmSelectListener.select(integer, list.get(integer));
            }
            return null;
        });
        mDataBinding.recyclerView.setAdapter(adapter);
        return this;
    }

    public BottomActionDialog setTitle(String title) {
        mDataBinding.tvTitle.setText(title);
        mDataBinding.llTitle.setVisibility(View.VISIBLE);
        return this;
    }

    public BottomActionDialog setTitle(int titleId) {
        mDataBinding.tvTitle.setText(titleId);
        mDataBinding.llTitle.setVisibility(View.VISIBLE);
        return this;
    }

    public BottomActionDialog setCancelText(int textId) {
        mDataBinding.tvCancel.setText(textId);
        return this;
    }

    public BottomActionDialog setCancelText(String text) {
        mDataBinding.tvCancel.setText(text);
        return this;
    }

    public BottomActionDialog setOnCancelClickListener(View.OnClickListener cancelClickListener) {
        this.mOnCancelClickListener = cancelClickListener;
        return this;
    }

    public BottomActionDialog setOnConfirmSelectListener(OnConfirmSelectListener confirmSelectListener) {
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
