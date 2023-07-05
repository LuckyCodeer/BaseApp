package com.lib_common.view.layout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lib_common.R;
import com.lib_common.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 横向tab 按钮控件
 * created by yhw
 * date 2022/9/1
 */
public class TabButtonLayout extends LinearLayout {
    private static final String TAG = TabButtonLayout.class.getSimpleName();
    private LinearLayout mLlContent;
    private TextView mSliderView;
    private CharSequence[] mItems;
    private final List<TextView> mButtonList = new ArrayList<>();
    private OnSelectedListener mOnSelectedListener;
    private int mSelectedPosition = 0;
    private ObjectAnimator mObjectAnimator;

    public TabButtonLayout(Context context) {
        super(context);
        init(null);
    }

    public TabButtonLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TabButtonLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setOrientation(HORIZONTAL);
        LayoutInflater.from(getContext()).inflate(R.layout.tab_button_layout, this);
        mLlContent = findViewById(R.id.ll_content);
        mSliderView = findViewById(R.id.slider_view);
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.TabButtonLayout);
        mItems = ta.getTextArray(R.styleable.TabButtonLayout_items);
        ta.recycle();
        initTab();
    }

    /**
     * 初始化tab
     */
    private void initTab() {
        if (mItems == null || mItems.length == 0) {
            return;
        }
        int i = 0;
        for (CharSequence item : mItems) {
            TextView textView = new TextView(getContext());
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams.width = 0;
            layoutParams.weight = 1;
            textView.setLayoutParams(layoutParams);

            if (i == mSelectedPosition) {
                textView.setTextColor(getResources().getColor(com.lib_src.R.color.white));
                post(() -> {
                    FrameLayout.LayoutParams sliderLayoutParams = (FrameLayout.LayoutParams) mSliderView.getLayoutParams();
                    sliderLayoutParams.width = textView.getWidth();
                    sliderLayoutParams.height = getHeight();
                    mSliderView.setLayoutParams(sliderLayoutParams);
                });
            } else {
                textView.setTextColor(getResources().getColor(com.lib_src.R.color.black_666666));
            }

            textView.setText(item);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(DisplayUtil.dip2px(getContext(), 10), DisplayUtil.dip2px(getContext(), 11),
                    DisplayUtil.dip2px(getContext(), 10), DisplayUtil.dip2px(getContext(), 11));

            mButtonList.add(textView);
            int index = i;
            textView.setOnClickListener(v -> {
                if (mSelectedPosition == index) {
                    return;
                }
                mSelectedPosition = index;
                changeButtonState(mSelectedPosition, textView);
            });
            mLlContent.addView(textView);
            i++;
        }
    }

    /**
     * 改变按钮状态
     *
     * @param position 当前选中索引
     * @param view     当前选中view
     */
    private void changeButtonState(int position, TextView view) {
        if (view != null) {
            if (mObjectAnimator != null) {
                mObjectAnimator.cancel();
                mObjectAnimator = null;
            }
            mObjectAnimator = ObjectAnimator.ofFloat(mSliderView, "translationX", view.getX());
            mObjectAnimator.setDuration(200);
            mObjectAnimator.setInterpolator(new LinearInterpolator());
            mObjectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    for (int i = 0; i < mItems.length; i++) {
                        TextView textView = mButtonList.get(i);
                        textView.setTextColor(position == i ? getResources().getColor(com.lib_src.R.color.white) : getResources().getColor(com.lib_src.R.color.black_666666));
                    }
                    if (mOnSelectedListener != null) {
                        mOnSelectedListener.onSelected(position, view);
                    }
                }
            });
            mObjectAnimator.start();
        }
    }

    /**
     * 设置选中索引
     *
     * @param selectedPosition 索引
     */
    public void setSelectedPosition(int selectedPosition) {
        if (this.mSelectedPosition == selectedPosition) {
            return;
        }
        this.mSelectedPosition = selectedPosition;
        post(() -> changeButtonState(selectedPosition, (TextView) mLlContent.getChildAt(selectedPosition)));
    }

    public int getSelectedPosition() {
        return mSelectedPosition;
    }

    /**
     * 设置选中事件
     */
    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
        this.mOnSelectedListener = onSelectedListener;
    }

    public interface OnSelectedListener {
        void onSelected(int position, View view);
    }

}
