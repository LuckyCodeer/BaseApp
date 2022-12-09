package com.huodada;

import androidx.appcompat.app.AppCompatDelegate;

import com.hjq.permissions.Permission;
import com.hjq.toast.ToastUtils;
import com.huodada.databinding.ActivityMainBinding;
import com.huodada.lib_common.base.BaseDataBindingActivity;
import com.huodada.lib_common.dialog.BottomActionDialog;
import com.huodada.lib_common.dialog.BottomListDialog;
import com.huodada.lib_common.dialog.CommonAlertDialog;
import com.huodada.lib_common.dialog.DateSelectDialog;
import com.huodada.lib_common.entity.Friend;
import com.huodada.lib_common.router.RouterUtils;
import com.huodada.lib_common.utils.ImageUtils;
import com.huodada.lib_common.utils.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面
 */
public class MainActivity extends BaseDataBindingActivity<ActivityMainBinding> {

    @Override
    protected void initView() {
        super.initView();
        setTitle(R.string.app_name);
        hideActionBarBack();
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
    }

}