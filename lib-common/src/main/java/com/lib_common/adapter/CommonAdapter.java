package com.lib_common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.DataBindingHolder;

import java.util.List;

/**
 * recycler通用adapter, 在BaseQuickAdapter基础上进一步简化使用
 * created by yhw
 * date 2022/12/6
 *
 * @param <T>  数据类
 * @param <DB> 布局自动生成的DataBinding类，要先建布局才能生成
 */
public abstract class CommonAdapter<T, DB extends ViewDataBinding> extends BaseQuickAdapter<T, DataBindingHolder<DB>> {
    public CommonAdapter() {
    }

    public CommonAdapter(@NonNull List<? extends T> items) {
        super(items);
    }

    @Override
    protected void onBindViewHolder(@NonNull DataBindingHolder<DB> dataBindingHolder, int position, @Nullable T t) {
        onBindViewHolder(dataBindingHolder, dataBindingHolder.getBinding(), position, t);
    }

    @NonNull
    @Override
    protected DataBindingHolder<DB> onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int viewType) {
        return new DataBindingHolder<>(DataBindingUtil.inflate(LayoutInflater.from(context),
                getItemLayoutId(viewType), viewGroup, false));
    }

    /**
     * 绑定数据
     * @param holder viewHolder
     * @param dataBinding dataBinding
     * @param position 索引
     * @param t 数据类
     */
    public abstract void onBindViewHolder(@NonNull DataBindingHolder<DB> holder, DB dataBinding, int position, @Nullable T t);

    /**
     * 设置布局
     * @param viewType 布局类型，用于一个列表多种item布局判断
     */
    public abstract int getItemLayoutId(int viewType);
}
