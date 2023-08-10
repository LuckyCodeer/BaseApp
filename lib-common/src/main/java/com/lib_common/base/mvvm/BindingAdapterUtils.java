package com.lib_common.base.mvvm;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
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
                .into(imageView);
    }

    /**
     * 日期格式化
     *
     * @param timestamp 时间戳
     */
    @BindingAdapter(value = {"dateTime"})
    public static void setDateTime(TextView textView, long timestamp) {
        if (timestamp == 0) {
            return;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        textView.setText(format.format(new Date(timestamp)));
    }
}
