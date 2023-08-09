package com.lib_common.base.mvvm;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

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
}
