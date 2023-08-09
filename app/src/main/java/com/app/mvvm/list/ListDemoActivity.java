package com.app.mvvm.list;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.app.R;
import com.app.databinding.ActivityDemoBinding;
import com.hjq.toast.ToastUtils;
import com.lib_common.base.mvvm.BaseMvvmActivity;
import com.lib_common.entity.ProjectResponse;
import com.lib_common.router.RouterPath;
import com.lib_common.router.RouterUtils;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * List Demo
 */
@Route(path = RouterPath.DEMO_ACTIVITY)
public class ListDemoActivity extends BaseMvvmActivity<ActivityDemoBinding, ListViewModel> {
    private int mPageNum = 1;

    @Override
    protected void initView() {
        super.initView();
        setTitle("这是一个列表");
        mDataBinding.refreshLayout.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        requestData();
    }

    private void requestData() {
        mViewModel.requestData(this, mPageNum);
    }

    @Override
    protected void observeDataChange() {
        super.observeDataChange();

        mViewModel.getListData().observe(this, projectResponse -> {
            MyListAdapter adapter = new MyListAdapter(projectResponse.getDatas());
            mDataBinding.refreshLayout.setAdapter(adapter, mPageNum, projectResponse.getTotal());
        });
    }

    @Override
    protected void onViewEvent() {
        //同时设置下拉刷新和上拉加载更多
        mDataBinding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPageNum++;
                requestData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPageNum = 1;
                requestData();
            }
        });

        //列表item单击事件
        mDataBinding.refreshLayout.setOnItemClickListener((itemView, position, item) -> {
            ProjectResponse.ProjectItem projectItem = (ProjectResponse.ProjectItem) item;
            RouterUtils.jumpWeb(projectItem.getLink());
        });

        //列表item长按事件
        mDataBinding.refreshLayout.setOnItemLongClickListener((itemView, position, item) -> {
            ProjectResponse.ProjectItem projectItem = (ProjectResponse.ProjectItem) item;
            ToastUtils.show("长按事件：" + projectItem.getTitle());
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_demo;
    }


    @Override
    protected int getVariableId() {
        return 0;
    }
}