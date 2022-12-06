package com.huodada;

import android.content.Intent;

import androidx.annotation.Nullable;

import com.hjq.toast.ToastUtils;
import com.huodada.databinding.ActivityMainBinding;
import com.huodada.lib_common.base.BaseDataBindingActivity;
import com.huodada.lib_common.dialog.DateSelectDialog;
import com.huodada.lib_common.router.RouterUtils;
import com.huodada.lib_common.utils.ImagePickerUtils;
import com.zhihu.matisse.Matisse;

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

    @Override
    protected void onViewEvent() {
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
            ImagePickerUtils.openGallery(this, 100, 10);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (data == null) {
                return;
            }
            //图片选择地址
            List<String> list = Matisse.obtainPathResult(data);
            ToastUtils.showLong(list.toString());
        }
    }
}