package com.app.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import com.app.R;
import com.app.databinding.ActivityDemo1Binding;
import com.hjq.toast.ToastUtils;

public class Demo1Activity extends AppCompatActivity {
    private ActivityDemo1Binding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_demo1);
        MyViewModel model = new ViewModelProvider(this).get(MyViewModel.class);

        String userName = null;
        String nickName = "lisi";
        mDataBinding.setUserName(userName);
        mDataBinding.setNickName(nickName);

        int colorType = 0;
        mDataBinding.setColorType(colorType);

        long l = System.currentTimeMillis();
        mDataBinding.setTime(l);

        MyViewModel vm = new MyViewModel();
        vm.setName("lsi");
        mDataBinding.setVm(vm);

    }


}