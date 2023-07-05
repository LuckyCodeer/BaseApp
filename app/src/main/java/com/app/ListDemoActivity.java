package com.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.app.databinding.ActivityDemoBinding;
import com.app.databinding.DemoItemLayoutBinding;
import com.chad.library.adapter.base.viewholder.DataBindingHolder;
import com.hjq.toast.ToastUtils;
import com.lib_common.adapter.CommonAdapter;
import com.lib_common.base.BaseDataBindingActivity;
import com.lib_common.entity.Friend;
import com.lib_common.http.HttpListener;
import com.lib_common.http.HttpUtils;
import com.lib_common.router.RouterPath;
import com.lib_common.router.RouterUtils;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

/**
 * Demo
 */
@Route(path = RouterPath.DEMO_ACTIVITY)
public class ListDemoActivity extends BaseDataBindingActivity<ActivityDemoBinding> {
    private int pageNum = 1;

    @Override
    protected void initView() {
        super.initView();
        setTitle("这是一个列表");
        requestData();
    }

    /**
     * 网络数据请求
     */
    private void requestData() {
        showLoading();
        HttpUtils.friend(this, new HttpListener<List<Friend>>() {
            @Override
            public void onSuccess(List<Friend> friends) {
                dismissLoading();
                MyAdapter adapter = new MyAdapter(friends);
                mDataBinding.refreshLayout.setAdapter(adapter, pageNum, 10000);
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoading();
                ToastUtils.show(errorMsg);
            }
        });
    }

    @Override
    protected void onViewEvent() {
        //单独设置下拉刷新
        mDataBinding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            pageNum = 1;
            requestData();
        });

        //单独设置上拉加载更多
        mDataBinding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            pageNum++;
            requestData();
        });

        //同时设置下拉刷新和上拉加载更多
        mDataBinding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNum++;
                requestData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                requestData();
            }
        });

        //列表item单击事件
        mDataBinding.refreshLayout.setOnItemClickListener((itemView, position, item) -> {
            Friend friend = (Friend) item;
            RouterUtils.jumpWeb(friend.getLink());
        });

        //列表item长按事件
        mDataBinding.refreshLayout.setOnItemLongClickListener((itemView, position, item) -> {
            Friend friend = (Friend) item;
            ToastUtils.show("长按事件：" + friend.getName());
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_demo;
    }


    /**
     * 列表适配器，一般建议写到外面，仅demo演示
     */
    private static class MyAdapter extends CommonAdapter<Friend, DemoItemLayoutBinding> {

        public MyAdapter(@NonNull List<? extends Friend> items) {
            super(items);
        }

        @Override
        public void onBindViewHolder(@NonNull DataBindingHolder<DemoItemLayoutBinding> holder, DemoItemLayoutBinding dataBinding, int position, @Nullable Friend friend) {
            //将数据类设置给布局文件
            dataBinding.setFriend(friend);
            //如果布局里还有其它逻辑，如颜色判断，隐藏显示，点击事件等，可用： dataBinding.xxx  xxx代表布局里控件的ID
//            dataBinding.tvContent.setTextColor(getContext().getResources().getColor(R.color.teal_700));
            //如果用到context 则使用   getContext()
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.demo_item_layout;
        }
    }
}