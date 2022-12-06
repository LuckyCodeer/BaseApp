package com.huodada.lib_common.dialog;

import android.content.Context;
import android.text.TextUtils;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.huodada.lib_src.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

/**
 * 日期选择控件
 */
public class DateSelectDialog {
    private TimePickerView mTimePicker;
    private final TimePickerBuilder mTimePickerBuilder;
    private Calendar mStartDate;
    private Calendar mEndDate;
    private Calendar mCurrDate;
    private boolean isEndDateEqCurrDate; //结束时间范围是否为当前时间

    public DateSelectDialog(Context context, OnTimeSelectListener listener) {
        this(context, "选择日期", listener);
    }

    public DateSelectDialog(Context context, String titleStr, OnTimeSelectListener listener) {
        mTimePickerBuilder = new TimePickerBuilder(context, listener)
                .setTitleBgColor(context.getResources().getColor(R.color.white))
                .setBgColor(context.getResources().getColor(R.color.white))
                .setTitleSize(16)
                .setTitleColor(context.getResources().getColor(R.color.black_333333))
                .setSubmitText(context.getString(R.string.confirm))
                .setSubmitColor(context.getResources().getColor(R.color.main_color))
                .setSubCalSize(16)
                .setCancelText(context.getString(R.string.cancel))
                .setCancelColor(context.getResources().getColor(R.color.black_999999))
                .setContentTextSize(15)
                .setTitleText(titleStr)
                .setDividerColor(context.getResources().getColor(R.color.main_color))
                .setTextColorCenter(context.getResources().getColor(R.color.main_color))
                .setOutSideCancelable(true)
                .isCyclic(false)
                .isCenterLabel(false)
                .setLineSpacingMultiplier(2.5f);
        setLabel("年", "月", "日", "时", "分", "秒");
    }

    /**
     * new boolean[]{true, true, true, false, false, false}
     * control the "year","month","day","hours","minutes","seconds " display or hide.
     * 分别控制“年”“月”“日”“时”“分”“秒”的显示或隐藏。
     *
     * @param type 布尔型数组，长度需要设置为6。
     * @return TimePickerBuilder
     */
    public DateSelectDialog setType(boolean[] type) {
        mTimePickerBuilder.setType(type);
        return this;
    }

    /**
     * 设置选择日期的粒度， 默认到日
     *
     * @param length 如1代表到年 2代表到月 依次类推
     */
    public DateSelectDialog setType(int length) {
        boolean[] types = new boolean[]{false, false, false, false, false, false};
        for (int i = 0; i < length; i++) {
            types[i] = true;
        }
        mTimePickerBuilder.setType(types);
        return this;
    }

    public DateSelectDialog setLabel(String label_year, String label_month, String label_day, String label_hours, String label_mins, String label_seconds) {
        mTimePickerBuilder.setLabel(label_year, label_month, label_day, label_hours, label_mins, label_seconds);
        return this;
    }

    public DateSelectDialog build() {
        mTimePicker = mTimePickerBuilder.setDate(mCurrDate)
                .setRangDate(mStartDate, this.isEndDateEqCurrDate ? Calendar.getInstance() : mEndDate).build();
        return this;
    }

    public DateSelectDialog build(Calendar startRangeDate, Calendar endRangeDate) {
        mTimePicker = mTimePickerBuilder.setDate(mCurrDate)
                .setRangDate(startRangeDate, endRangeDate).build();
        return this;
    }

    /**
     * 设置开始时间范围
     *
     * @param startDate 开始时间范围
     */
    public DateSelectDialog setRangStartDate(Calendar startDate) {
        this.mStartDate = startDate;
        return this;
    }

    /**
     * 设置开始日期 仅控制年
     *
     * @param startYear 开始年
     */
    public DateSelectDialog setRangStartDate(int startYear) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, startYear);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        this.mStartDate = calendar;
        return this;
    }


    /**
     * 设置结束时间范围 默认为当前时间
     */
    public DateSelectDialog setRangEndDate() {
        this.isEndDateEqCurrDate = true;
        this.mEndDate = Calendar.getInstance();
        return this;
    }

    /**
     * 设置结束时间范围
     *
     * @param endDate 开始时间范围
     */
    public DateSelectDialog setRangEndDate(Calendar endDate) {
        this.isEndDateEqCurrDate = false;
        this.mEndDate = endDate;
        return this;
    }

    public DateSelectDialog setRangEndDate(int endYear) {
        this.isEndDateEqCurrDate = false;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, endYear);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        this.mEndDate = calendar;
        return this;
    }

    /**
     * 设置时间范围
     *
     * @param startDate 开始时间范围
     * @param endDate   结束时间范围
     */
    public DateSelectDialog setRangDate(Calendar startDate, Calendar endDate) {
        this.mStartDate = startDate;
        this.mEndDate = endDate;
        return this;
    }

    /**
     * 设置默认时间
     */
    public DateSelectDialog setDate(Calendar date) {
        this.mCurrDate = date;
        return this;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitleText(String title) {
        if (mTimePicker != null) {
            mTimePickerBuilder.setTitleText(title);
            mTimePicker.setTitleText(title);
        }
    }

    /**
     * 显示
     *
     * @param dateTime 当前选择时间
     */
    public void show(String dateTime) {
        try {
            Calendar calendar = Calendar.getInstance();
            if (!TextUtils.isEmpty(dateTime)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                calendar.setTime(Objects.requireNonNull(simpleDateFormat.parse(dateTime)));
            }
            show(calendar);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示
     *
     * @param dateTime 当前选择时间
     */
    public void show(Calendar dateTime) {
        if (dateTime.getTimeInMillis() > Calendar.getInstance().getTimeInMillis() || (mStartDate != null && dateTime.getTimeInMillis() < mStartDate.getTimeInMillis())) {
            this.mCurrDate = Calendar.getInstance();
        } else {
            this.mCurrDate = dateTime;
        }
        show(true);
    }

    /**
     * 显示
     *
     * @param isRebuild 是否重构
     */
    public void show(boolean isRebuild) {
        if (isRebuild) {
            build();
        }
        show();
    }

    /**
     * 显示
     */
    public void show() {
        if (mTimePicker != null) {
            mTimePicker.show();
        }
    }
}
