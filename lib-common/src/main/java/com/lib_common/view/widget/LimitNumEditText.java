package com.lib_common.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lib_common.R;
import com.lib_common.listener.SimpleTextWatcher;

import org.jetbrains.annotations.NotNull;

/**
 * 限制输入数字(包括整数和小数)的 EditText
 * 如限制小数位数
 * 限制最大输入数值等
 */
public class LimitNumEditText extends ZeroAutoClearEditText {
    private static final String TAG = LimitNumEditText.class.getSimpleName();
    private int mDecimalsNum = 2; //限制输入小数位数 ,默认为2位
    private int mMaxNum = -1; //限制最大输入的数字 -1位不限制

    public LimitNumEditText(@NonNull @NotNull Context context) {
        super(context);
        init(null);
    }

    public LimitNumEditText(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LimitNumEditText(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        try {
            if (attrs != null) {
                TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.LimitNumEditText);
                mDecimalsNum = ta.getInt(R.styleable.LimitNumEditText_decimalsNum, 2); //默认可输入两位小数
                mMaxNum = ta.getInt(R.styleable.LimitNumEditText_maxNum, -1);
                ta.recycle();
            }

            addTextChangedListener(new SimpleTextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    super.afterTextChanged(s);
                    if (s.length() == 0 || TextUtils.isEmpty(s.toString().trim())) {
                        return;
                    }
                    String editStr = s.toString().trim();
                    //判断是否包含小数
                    int start = editStr.lastIndexOf(".");
                    if (start != -1) {
                        //如果第一位输入了小数点，则去掉小数点
                        if (start == 0) {
                            setText(editStr.replace(".", ""));
                            return;
                        }
                        //如果限制小数位数为0 相当于仅允许输入整数，不能输入小数点
                        if (mDecimalsNum == 0) {
                            setText(editStr.replace(".", ""));
                            return;
                        }
                        //当前输入小数位数
                        int decimals = editStr.length() - (start + 1);
                        if (mDecimalsNum > 0 && decimals > mDecimalsNum) {
                            s.delete(start + 1 + mDecimalsNum, editStr.length());
                            return;
                        }
                    }

                    //判断是否输入了大于最大允许输入的数字
                    if (mMaxNum > 0 && Double.parseDouble(editStr) > mMaxNum) {
                        setText(editStr.substring(0, editStr.length() - 1));
                        setSelection(getText().length());
                    }

                    //处理前面是否有多个0的情况
                    handlePreZero(s.toString());
                }
            });
            //设置只允许输入整数或者小数
            setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            //是否设置了最大长度
            boolean hasMaxLength = false;
            for (InputFilter filter : getFilters()) {
                if (filter instanceof InputFilter.LengthFilter) {
                    hasMaxLength = true;
                    break;
                }
            }
            //如果没设置，默认设置允许输入的最大长度为15，避免用户无限输入
            if (!hasMaxLength) {
                addFilters(new InputFilter.LengthFilter(15));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断最前面的数字是否输入为0，避免最前面输入多个0的情况
     */
    private void handlePreZero(String s) {
        int start = s.lastIndexOf(".");
        //先判断是否有0 或者最前面是否为0
        if (!s.contains("0") || s.indexOf("0") != 0 || start == s.length() - 1) {
            return;
        }
        int maxZero = 0; //最大允许前面存在几个0
        //包含小数
        if (start != -1) {
            final String first = s.substring(1, 2);
            //已经输入的第二位是小数点.
            if (".".equals(first)) {
                maxZero = 1;
            }
        }
        //第一位输入0
        if (s.length() == 1) {
            maxZero = 1;
        }
        int curZero = 0; //当前前面0的个数
        for (int i = 0; i < s.length(); i++) {
            if (!"0".equals(s.substring(i, i + 1))) {
                break;
            }
            curZero++;
        }
        Log.i(TAG, "maxZero: " + maxZero + " , curZero: " + curZero + " , s: " + s);
        //去掉多余的0
        if (curZero > maxZero) {
            if ("00".equals(s)) {
                setText("0");
            } else {
                setText(s.substring(curZero - maxZero));
            }
        }
    }

    /**
     * 动态设置小数位数
     *
     * @param decimalsNum 小数位数
     */
    public void setDecimalsNum(int decimalsNum) {
        this.mDecimalsNum = decimalsNum;
        setText(getText());
    }

    /**
     * 动态设置最大输入数
     *
     * @param maxNum 最大输入数
     */
    public void setMaxNum(int maxNum) {
        this.mMaxNum = maxNum;
        setText(getText());
    }
}
