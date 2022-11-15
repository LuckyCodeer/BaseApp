package com.huodada.lib_common.view.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huodada.lib_common.R;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/**
 * SmartRefreshLayout 封装
 * created by yhw
 * date 2022/11/14
 */
public class BaseSmartRefreshLayout extends FrameLayout {
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private View mEmptyLayout; //空布局
    private TextView mTvEmpty; //空布局提示文本
    private String mDefaultEmptyText; //默认空布局文本提示文案

    private BaseQuickAdapter mAdapter;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public BaseSmartRefreshLayout(Context context) {
        super(context);
        init(context, null);
    }

    public BaseSmartRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.base_list_layout, this);
        mRefreshLayout = findViewById(R.id.base_refresh_layout);
        mRecyclerView = findViewById(R.id.recycler_view);

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BaseSmartRefreshLayout);
            mRefreshLayout.setEnableRefresh(ta.getBoolean(R.styleable.BaseSmartRefreshLayout_enableRefresh, true));
            mRefreshLayout.setEnableLoadMore(ta.getBoolean(R.styleable.BaseSmartRefreshLayout_enableLoadMore, true));
            mDefaultEmptyText = ta.getString(R.styleable.BaseSmartRefreshLayout_emptyText);
            ta.recycle();
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    /**
     * 无需分页的情况
     */
    public <T, VH extends RecyclerView.ViewHolder> void setAdapter(@Nullable BaseQuickAdapter<T, VH> adapter) {
        setAdapter(adapter, -1, -1);
    }

    /**
     * 分页的情况
     *
     * @param adapter  数据适配器
     * @param pageNum  第几页
     * @param pageSize 总页数
     */
    public <T, VH extends RecyclerView.ViewHolder> void setAdapter(@Nullable BaseQuickAdapter<T, VH> adapter, int pageNum, int pageSize) {
        if (adapter == null) {
            throw new NullPointerException("adapter not is null");
        }
        if (this.mAdapter == null) {
            this.mAdapter = adapter;
            setListener();
            setEmptyLayout();
            mRecyclerView.setAdapter(adapter);
            finishRefresh();
        } else {
            if (pageNum <= 1) {
                setItems(adapter.getItems());
                finishRefresh();
            } else {
                addAll(adapter.getItems());
                finishLoadMore();
            }
        }
        //判断数据是否已经加载完毕
        if (adapter.getItems().size() == 0 || this.mAdapter.getItems().size() >= pageSize) {
            finishLoadMoreWithNoMoreData();
        }
    }

    /**
     * 设置事件
     */
    private void setListener() {
        mAdapter.setOnItemClickListener((Function3<BaseQuickAdapter, View, Integer, Unit>) (baseQuickAdapter, view, integer) -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, integer, mAdapter.getItem(integer));
            }
            return null;
        });

        mAdapter.setOnItemLongClickListener((Function3<BaseQuickAdapter, View, Integer, Boolean>) (baseQuickAdapter, view, integer) -> {
            if (mOnItemLongClickListener != null) {
                mOnItemLongClickListener.onItemLongClick(view, integer, mAdapter.getItem(integer));
            }
            return null;
        });
    }

    /**
     * 设置空布局
     */
    @SuppressLint("InflateParams")
    private void setEmptyLayout() {
        mAdapter.setEmptyViewEnable(true);
        mEmptyLayout = LayoutInflater.from(getContext()).inflate(R.layout.empty_layout, null);
        mTvEmpty = mEmptyLayout.findViewById(R.id.tv_empty_content);
        if (mDefaultEmptyText != null) {
            mTvEmpty.setText(mDefaultEmptyText);
        }
        mAdapter.setEmptyView(mEmptyLayout);
    }

    public void setEmptyViewEnable(boolean enable) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.setEmptyViewEnable(enable);
    }

    public <T> void setItems(List<T> items) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.setItems(items);
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        if (mAdapter == null) {
            return;
        }
        mAdapter.notifyDataSetChanged();
    }

    public void notifyItemChanged(int position) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.notifyItemChanged(position);
    }

    public void removeAt(int position) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.removeAt(position);
    }

    public void notifyItemInserted(int position) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.notifyItemInserted(position);
    }

    public <T> void addAll(List<T> items) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.addAll(items);
    }

    public void finishRefresh() {
        mRefreshLayout.finishRefresh();
    }

    public void finishLoadMore() {
        mRefreshLayout.finishLoadMore();
    }

    public void finishLoadMoreWithNoMoreData() {
        mRefreshLayout.finishLoadMoreWithNoMoreData();
    }

    public void setEnableRefresh(boolean enable) {
        mRefreshLayout.setEnableRefresh(enable);
    }

    public void setEnableLoadMore(boolean enable) {
        mRefreshLayout.setEnableLoadMore(enable);
    }

    /**
     * 下拉刷新事件
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        mRefreshLayout.setOnRefreshListener(listener);
    }

    /**
     * 上拉加载更多事件
     */
    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mRefreshLayout.setOnLoadMoreListener(listener);
    }

    /**
     * 同时设置下拉刷新和上拉加载更多事件
     */
    public void setOnRefreshLoadMoreListener(OnRefreshLoadMoreListener listener) {
        mRefreshLayout.setOnRefreshLoadMoreListener(listener);
    }

    /**
     * 设置单击事件
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    /**
     * 设置长按事件
     */
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position, Object item);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View itemView, int position, Object item);
    }

    /**
     * 设置空布局文本提示
     *
     * @param text 提示
     */
    public void setEmptyText(String text) {
        if (this.mTvEmpty == null) {
            return;
        }
        this.mTvEmpty.setText(text);
    }

    public TextView getEmptyTextView() {
        return mTvEmpty;
    }

    public View getEmptyLayout() {
        return mEmptyLayout;
    }

    public SmartRefreshLayout getRefreshLayout() {
        return mRefreshLayout;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
