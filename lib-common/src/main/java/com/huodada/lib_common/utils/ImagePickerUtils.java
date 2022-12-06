package com.huodada.lib_common.utils;

import android.app.Activity;
import android.content.pm.ActivityInfo;

import com.huodada.lib_common.R;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

/**
 * 图片选择工具类
 * created by yhw
 * date 2022/12/6
 */
public class ImagePickerUtils {

    /**
     * 打开图库选择
     */
    public static void openGallery(Activity activity, int requestCode, int maxSelectable) {
        //选择图片
        Matisse.from(activity)
                .choose(MimeType.ofImage()) // 选择 mime 的类型
                .countable(true)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, activity.getPackageName() + "_photo_picker"))
                .maxSelectable(maxSelectable) // 图片选择的最多数量
                .theme(R.style.MatisseZhihuStyle) //主题
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f) // 缩略图的比例
//                .gridExpectedSize(120)
                .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                .forResult(requestCode); // 设置作为标记的请求码
    }

    public static void openGallerySingle(Activity activity, int requestCode) {
        openGallery(activity, requestCode, 1);
    }
}
