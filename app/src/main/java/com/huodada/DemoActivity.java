package com.huodada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.DataBindingHolder;
import com.hjq.toast.ToastUtils;
import com.huodada.databinding.ActivityDemoBinding;
import com.huodada.databinding.DemoItemLayoutBinding;
import com.huodada.lib_common.base.BaseDataBindingActivity;
import com.huodada.lib_common.entity.Friend;
import com.huodada.lib_common.http.HttpListener;
import com.huodada.lib_common.http.HttpUtils;
import com.huodada.lib_common.router.RouterPath;
import com.huodada.lib_common.view.layout.BaseSmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/**
 * Demo
 */
@Route(path = RouterPath.DEMO_ACTIVITY)
public class DemoActivity extends BaseDataBindingActivity<ActivityDemoBinding> {
    private int pageNum = 1;

    @Override
    protected void initView() {
        super.initView();
        setTitle(R.string.app_name);
//        View view = LayoutInflater.from(this)
//                .inflate(com.huodada.lib_common.R.layout.empty_layout, null);
////        mTvEmpty = view.findViewById(com.huodada.lib_common.R.id.tv_content);
//        mDataBinding.refreshLayout.setRefreshContent(view);
        requestData();
    }

    /**
     * 网络数据请求
     */
    private void requestData() {
        HttpUtils.friend(this, new HttpListener<List<Friend>>() {
            @Override
            public void onSuccess(List<Friend> friends) {
                friends.clear();
                MyAdapter adapter = new MyAdapter(friends);
            /*    adapter.setOnItemClickListener(new Function3<BaseQuickAdapter<Friend, ?>, View, Integer, Unit>() {
                    @Override
                    public Unit invoke(BaseQuickAdapter<Friend, ?> friendBaseQuickAdapter, View view, Integer integer) {
                        ToastUtils.show("33333");
                        return null;
                    }
                });*/
                mDataBinding.refreshLayout.setAdapter(adapter, pageNum, 10000);
//                ToastUtils.show("请求成功");
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    @Override
    protected void onViewEvent() {
       /* mDataBinding.refreshLayout.getRefreshLayout().setOnRefreshListener(refreshLayout -> {
            pageNum = 1;
            requestData();
        });

        mDataBinding.refreshLayout.getRefreshLayout().setOnLoadMoreListener(refreshLayout -> {
            pageNum ++;
            requestData();
        });
*/
        mDataBinding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNum ++;
                requestData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                requestData();
            }
        });

        mDataBinding.refreshLayout.setOnItemClickListener((itemView, position, item) -> {
            Friend friend = (Friend) item;
            ToastUtils.show(friend.getName());
        });
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