package com.huodada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.RegexUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.DataBindingHolder;
import com.hjq.toast.ToastUtils;
import com.huodada.databinding.ActivityDemoBinding;
import com.huodada.databinding.DemoItemLayoutBinding;
import com.huodada.lib_common.base.BaseDataBindingActivity;
import com.huodada.lib_common.entity.Friend;
import com.huodada.lib_common.http.HttpListener;
import com.huodada.lib_common.http.HttpUtils;

import java.util.List;

/**
 * Demo
 */
public class DemoActivity extends BaseDataBindingActivity<ActivityDemoBinding> {

    @Override
    protected void initView() {
        super.initView();
        requestData();
    }

    /**
     * 网络数据请求
     */
    private void requestData() {
        HttpUtils.friend(this, new HttpListener<List<Friend>>() {
            @Override
            public void onSuccess(List<Friend> friends) {
                MyAdapter adapter = new MyAdapter(friends);
                mDataBinding.recyclerView.setAdapter(adapter);
                ToastUtils.show("请求成功");
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    @Override
    protected void onViewEvent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_demo;
    }


    private static class MyAdapter extends BaseQuickAdapter<Friend, DataBindingHolder<DemoItemLayoutBinding>> {

        public MyAdapter(@NonNull List<? extends Friend> items) {
            super(items);
        }

        @Override
        protected void onBindViewHolder(@NonNull DataBindingHolder<DemoItemLayoutBinding> dataBindingHolder, int i, @Nullable Friend friend) {
            dataBindingHolder.getBinding().setFriend(friend);
        }

        @NonNull
        @Override
        protected DataBindingHolder<DemoItemLayoutBinding> onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
            return new DataBindingHolder<>(DataBindingUtil.inflate(LayoutInflater.from(context),
                    R.layout.demo_item_layout, viewGroup, false));
        }
    }
}