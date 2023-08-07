package com.app;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import com.app.databinding.ActivityMainBinding;
import com.app.mvvm.MvvmDemoActivity;
import com.hjq.permissions.Permission;
import com.hjq.toast.ToastUtils;
import com.lib_common.base.mvvm.BaseMvvmActivity;
import com.lib_common.base.mvvm.BaseViewModel;
import com.lib_common.dialog.BottomActionDialog;
import com.lib_common.dialog.BottomListDialog;
import com.lib_common.dialog.CommonAlertDialog;
import com.lib_common.dialog.DateSelectDialog;
import com.lib_common.entity.Friend;
import com.lib_common.router.RouterUtils;
import com.lib_common.utils.ImageUtils;
import com.lib_common.utils.PermissionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 主界面
 */
public class MainActivity extends BaseMvvmActivity<ActivityMainBinding, BaseViewModel> {
    private static final String TAG = "MainActivity";
    private TextToSpeech textToSpeech;

    @Override
    protected void initView() {
        super.initView();
        setTitle(R.string.app_name);
        hideActionBarBack();
        setNotificationBar();
    }

    private void setNotificationBar() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            items.add("这是一条长文本长文本长文本长文本长文本长文本横幅通知显示" + i);
        }
        mDataBinding.notificationBar.setData(items);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private String getModeText() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            return "正常模式";
        } else {
            return "深色模式";
        }
    }

    @Override
    protected void onViewEvent() {
        //模式设置
        mActionBar.setRightText(getModeText(), view -> {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            mActionBar.setRightText(getModeText());
        });

        //列表
        mDataBinding.btnDemo.setOnClickListener(view -> {
            RouterUtils.jumpDemo();
        });

        //网页
        mDataBinding.btnBrowser.setOnClickListener(view -> {
            RouterUtils.jumpWeb("https://jd.com");
        });

        //选择日期时间
        mDataBinding.btnSelectTime.setOnClickListener(view -> {
            new DateSelectDialog(this, (date, v) -> {

            }).build().show();
        });

        //选择图片
        mDataBinding.btnSelectPic.setOnClickListener(view -> {
            //申请权限
            PermissionUtil.requestPermission(this, new String[]{Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE},
                    () -> {
                        ImageUtils.openGallery(this, 5, new ImageUtils.OnResultCallback() {
                            @Override
                            public void onSimpleResult(List<String> result) {
                                ToastUtils.show(result.toString());
                            }
                        });
                    });
        });

        //预览图片
        mDataBinding.btnPreviewImages.setOnClickListener(view -> {
            ImageUtils.previewImages(this, "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2018-06-27%2F5b3345789ca2c.jpg&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1672995274&t=a391071aeb68e9f2364501cd9cc48e24");
        });

        //对话框
        mDataBinding.btnDialog.setOnClickListener(view -> {
            new CommonAlertDialog(this).setMessage("确定要删除吗？")
                    .setOnConfirmClickListener(v -> {
                        CommonAlertDialog.showDialog(this, "这条数据已经被删除！");
                    }).show();
        });

        //底部选择对话框1
        mDataBinding.btnSelectDialog.setOnClickListener(view -> {
            List<String> items = new ArrayList<>();
            items.add("红色");
            items.add("黄色");
            items.add("绿色");
            items.add("橙色");
            items.add("紫色");
            new BottomListDialog(this).setItems(items)
                    .setOnConfirmSelectListener((position, name) -> {
                        ToastUtils.show(name);
                    })
                    .show();
        });

        //底部选择对话框2
        mDataBinding.btnSelectDialog2.setOnClickListener(view -> {
            List<String> items = new ArrayList<>();
            items.add("拍照");
            items.add("相册");
            new BottomActionDialog(this).setItems(items)
                    .setOnConfirmSelectListener((position, name) -> {
                        ToastUtils.show(name);
                    }).show();
        });

        //Toast提示
        mDataBinding.btnToast.setOnClickListener(view -> {
            ToastUtils.show("这是一个Toast提示");
        });

        //异常捕获
        mDataBinding.btnException.setOnClickListener(view -> {
            Friend friend = null;
            ToastUtils.show(friend.getName());
        });

        //语音播报
        mDataBinding.btnSpeech.setOnClickListener(view -> {
            final String text = "支付宝到账1000000元，支付宝到账1000000元，支付宝到账1000000元";
            if (textToSpeech != null) {
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, new Bundle(), null);
                return;
            }
            textToSpeech = new TextToSpeech(this, status -> {
                Log.e(TAG, "onInit===> " + status);
                if (status == TextToSpeech.SUCCESS) {
                    // TTS引擎初始化成功
                    Locale language = Locale.getDefault(); // 获取当前系统语言
                    int result = textToSpeech.setLanguage(language); // 设置TTS引擎语言
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        // 不支持当前语言
                        Log.e(TAG, "Language not supported");
                        ToastUtils.show("不支持当前语言");
                    } else {
                        // TTS引擎初始化成功，可以进行后续操作
                        Log.i(TAG, "TTS engine initialized");
                        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, new Bundle(), null);
                    }
                } else {
                    // TTS引擎初始化失败
                    Log.e(TAG, "TTS engine initialization failed");
                    ToastUtils.show("TTS引擎初始化失败");
                }
            });
            // 设置音量(值越大声音越尖(女生)，值越小则变成男声，1.0是常规)
            textToSpeech.setPitch(1f);
            // 设置语速
            textToSpeech.setSpeechRate(1.0f);
            // 设置需要播报的语言
//            textToSpeech.setLanguage(Locale.getDefault());
            // 设置需要播报的语句(若设备不支持则不会读出来)
            /**
             * TextToSpeech.QUEUE_FLUSH：中断当时的播报，播报新的语音
             * TextToSpeech.QUEUE_ADD：添加到当前任务之后
             */
//            textToSpeech.speak("中午吃饭了吗", TextToSpeech.QUEUE_FLUSH, null);
        });

        //数据双向绑定
        mDataBinding.btnDataBinding.setOnClickListener(v -> {
            startActivity(new Intent(this, MvvmDemoActivity.class));
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    @Override
    protected int getVariableId() {
        return 0;
    }
}