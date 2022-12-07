package com.huodada.lib_common.utils;

import android.content.Context;
import android.os.Build;

import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnExternalPreviewEventListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureSelectorStyle;
import com.luck.picture.lib.style.TitleBarStyle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import top.zibin.luban.Luban;
import top.zibin.luban.OnNewCompressListener;

/**
 * 图片工具类
 * created by yhw
 * date 2022/12/6
 */
public class ImageUtils {

    /**
     * 获取图库样式
     */
    private static PictureSelectorStyle getStyle(Context context) {
        PictureSelectorStyle pictureSelectorStyle = new PictureSelectorStyle();
        TitleBarStyle titleBarStyle = new TitleBarStyle();
        titleBarStyle.setTitleBackgroundColor(context.getResources().getColor(com.huodada.lib_src.R.color.main_color));
        pictureSelectorStyle.setTitleBarStyle(titleBarStyle);
        return pictureSelectorStyle;
    }

    /**
     * 图片选择
     *
     * @param maxSelectable 最大选择图片数
     */
    public static void openGallery(Context context, int maxSelectable, OnResultCallback resultCallback) {
        PictureSelector.create(context)
                .openGallery(SelectMimeType.ofImage())
                .setSelectorUIStyle(getStyle(context))
                .setImageEngine(GlideEngine.createGlideEngine())
                .setMaxSelectNum(maxSelectable)
                //配置压缩
                .setCompressEngine((CompressFileEngine) (context1, source, call) -> Luban.with(context1).load(source).ignoreBy(100)
                        .setCompressListener(new OnNewCompressListener() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onSuccess(String source, File compressFile) {
                                if (call != null) {
                                    call.onCallback(source, compressFile.getAbsolutePath());
                                }
                            }

                            @Override
                            public void onError(String source, Throwable e) {
                                if (call != null) {
                                    call.onCallback(source, null);
                                }
                            }
                        }).launch())
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(ArrayList<LocalMedia> result) {
                        if (resultCallback != null) {
                            resultCallback.onResult(result);
                            final List<String> pathList = new ArrayList<>();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                for (LocalMedia media : result) {
                                    pathList.add(media.getCompressPath());
                                }
                            }
                            resultCallback.onSimpleResult(pathList);
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }

    /**
     * 图片选择-单选
     */
    public static void openGallery(Context context, OnResultCallback resultCallback) {
        openGallery(context, 1, resultCallback);
    }

    /**
     * 预览图片-多图
     *
     * @param position 当前图片索引
     * @param urls     图片列表地址
     */
    public static void previewImages(Context context, int position, List<String> urls) {
        final ArrayList<LocalMedia> list = new ArrayList<>();
        int i = 0;
        for (String url : urls) {
            LocalMedia localMedia = new LocalMedia();
            localMedia.position = i;
            localMedia.setPath(url);
            list.add(localMedia);
            i++;
        }
        PictureSelector.create(context)
                .openPreview()
                .setImageEngine(GlideEngine.createGlideEngine())
                .setSelectorUIStyle(getStyle(context))
                .setExternalPreviewEventListener(new OnExternalPreviewEventListener() {
                    @Override
                    public void onPreviewDelete(int position) {

                    }

                    @Override
                    public boolean onLongPressDownload(LocalMedia media) {
                        return false;
                    }
                })
                .startActivityPreview(position, false, list);
    }

    /**
     * 预览图片-单图
     */
    public static void previewImages(Context context, String url) {
        final List<String> list = new ArrayList<>();
        list.add(url);
        previewImages(context, 0, list);
    }

    public interface OnResultCallback {
        /**
         * 返回完整图片属性
         */
        default void onResult(ArrayList<LocalMedia> result) {

        }

        /**
         * 只返回压缩后的图片地址列表
         */
        default void onSimpleResult(List<String> result) {

        }
    }
}
