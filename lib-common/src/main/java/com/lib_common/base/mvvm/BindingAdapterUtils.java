package com.lib_common.base.mvvm;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 通用@BindingAdapter
 * 这里只放置通用的方法，和业务相关的方法请放到业务模块下
 * created by yhw
 * date 2023/8/9
 */
public class BindingAdapterUtils {

    /**
     * 加载网络图片
     *
     * @param imageUrl 图片地址
     */
    @BindingAdapter(value = {"imageUrl"}, requireAll = false)
    public static void loadImage(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext()).load(imageUrl)
                .placeholder(com.lib_src.R.drawable.icon_placeholder)
                .error(com.lib_src.R.drawable.icon_placeholder)
                .into(imageView);
    }

    /**
     * 日期格式化
     *
     * @param timestamp 时间戳
     */
    @BindingAdapter(value = {"dateTime", "format"}, requireAll = false)
    public static void setDateTime(TextView textView, long timestamp, String format) {
        if (timestamp == 0) {
            return;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
        textView.setText(simpleDateFormat.format(new Date(timestamp)));
    }
}
