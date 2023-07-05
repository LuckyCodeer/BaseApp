package com.lib_common.view.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

import com.chad.library.adapter.base.viewholder.QuickViewHolder;
import com.lib_common.R;
import com.xiaweizi.marquee.MarqueeTextView;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.listener.OnPageChangeListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通知栏控件
 * created by yhw
 * date 2022/10/20
 */
public class NotificationBar extends LinearLayout {
    private static final String TAG = NotificationBar.class.getSimpleName();
    private Banner<String, MyAdapter> mBanner;
    private boolean isShow; //是否显示
    private final Map<Integer, MarqueeTextView> mViewMap = new HashMap<>();
    private boolean isFirst = true;

    public NotificationBar(Context context) {
        super(context);
        init(null);
    }

    public NotificationBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public NotificationBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.notification_bar_layout, this);
        mBanner = findViewById(R.id.banner);
        mBanner.addBannerLifecycleObserver((LifecycleOwner) getContext());
        mBanner.setUserInputEnabled(false);
    }

    /**
     * 设置数据
     */
    public void setData(List<String> msgList) {
        isFirst = true;
        if (msgList == null) {
            isShow = false;
            setVisibility(View.GONE);
            return;
        }
        isShow = true;
        setVisibility(View.VISIBLE);
        mBanner.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                postDelayed(() -> {
                    Log.i(TAG, "onPageSelected==> " + position);
                    for (Map.Entry<Integer, MarqueeTextView> entry : mViewMap.entrySet()) {
                        final MarqueeTextView textView = mViewMap.get(position);
                        if (textView == null) {
                            continue;
                        }
                        textView.stopScroll();
                        if (position == entry.getKey()) {
                            textView.setRndDuration(!TextUtils.isEmpty(textView.getText()) ? textView.getText().length() * 350 : 16000);
                            textView.startScroll();
                            if (position == 0) {
                                isFirst = true;
                            }
                        }
                    }
                }, 200);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        MyAdapter adapter = new MyAdapter(getContext(), msgList);
        mBanner.setAdapter(adapter, true);
        //单条不轮播
        if (msgList.size() == 1) {
            mBanner.isAutoLoop(false);
        } else {
            mBanner.isAutoLoop(true);
            mBanner.setLoopTime(5000L);
            mBanner.start();
        }
    }

    private class MyAdapter extends BannerAdapter<String, QuickViewHolder> {
        private final Context mContext;

        public MyAdapter(Context context, List<String> datas) {
            super(datas);
            this.mContext = context;
        }

        @Override
        public QuickViewHolder onCreateHolder(ViewGroup parent, int viewType) {
            return new QuickViewHolder(LayoutInflater.from(mContext).inflate(R.layout.notification_bar_item_layout, parent, false));
        }

        @Override
        public void onBindView(QuickViewHolder holder, String data, int position, int size) {
            Log.i(TAG, "onBindView==> " + position + "  " + isFirst);
            final MarqueeTextView textView = holder.getView(R.id.tv_msg);
            mViewMap.put(position, textView);
            textView.setText(data);
            textView.stopScroll();
            if (position == 0 && isFirst) {
                isFirst = false;
                textView.setRndDuration(!TextUtils.isEmpty(textView.getText()) ? textView.getText().length() * 350 : 16000);
                textView.startScroll();
            }
        }
    }

    public boolean isShow() {
        return isShow;
    }

    public Banner<String, MyAdapter> getBanner() {
        return mBanner;
    }

    public void setOrientation(int orientation) {
        mBanner.setOrientation(orientation);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mBanner != null) {
            if (mViewMap.size() > 0) {
                for (Map.Entry<Integer, MarqueeTextView> entry : mViewMap.entrySet()) {
                    entry.getValue().stopScroll();
                }
            }
            mBanner.destroy();
        }
    }
}
