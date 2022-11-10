package com.huodada.lib_common.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huodada.lib_common.R;
import com.huodada.lib_common.utils.MathUtil;

import org.jetbrains.annotations.NotNull;

/**
 * 如果输入框数字为0
 * 获取焦点后自动清空输入框
 */
public class ZeroAutoClearEditText extends ClearEditText {
    private String mTempContent; //临时存储0值变量
    private boolean isClearZero = true; //获取焦点后是否清除0值

    public ZeroAutoClearEditText(@NonNull @NotNull Context context) {
        super(context);
        init(null);
    }

    public ZeroAutoClearEditText(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ZeroAutoClearEditText(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.ZeroAutoClearEditText);
        isClearZero = ta.getBoolean(R.styleable.ZeroAutoClearEditText_clearZero, true);
        ta.recycle();
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            if (getText() == null) {
                return;
            }
            String content = getText().toString();
            if (content.length() > 0) {
                if (MathUtil.isZero(content) && isClearZero) {
                    mTempContent = content;
                    setText(null);
                } else {
                    // 光标定位到内容后
                    this.post(() -> setSelection(content.length()));
                }
            }
        } else {
            if (!TextUtils.isEmpty(mTempContent) && TextUtils.isEmpty(getText())) {
                setText(mTempContent);
            }
        }
    }

}
