package com.lib_common.view.layout.form;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.lib_common.R;

/**
 * form表单item
 * created by yhw
 * date 2023/7/5
 */
public class FormItemLayout extends LinearLayout {
    public FormItemLayout(Context context) {
        super(context);
        init(null);
    }

    public FormItemLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FormItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.form_item_layout, this);
    }
}
