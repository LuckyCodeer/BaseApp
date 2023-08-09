package com.app.mvvm.list;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.R;
import com.app.databinding.DemoItemLayoutBinding;
import com.chad.library.adapter.base.viewholder.DataBindingHolder;
import com.lib_common.adapter.CommonAdapter;
import com.lib_common.entity.ProjectResponse;

import java.util.List;

/**
 * created by yhw
 * date 2023/8/9
 */
public class MyListAdapter extends CommonAdapter<ProjectResponse.ProjectItem, DemoItemLayoutBinding> {

    public MyListAdapter(@NonNull List<? extends ProjectResponse.ProjectItem> items) {
        super(items);
    }

    @Override
    public void onBindViewHolder(@NonNull DataBindingHolder<DemoItemLayoutBinding> holder, DemoItemLayoutBinding dataBinding, int position, @Nullable ProjectResponse.ProjectItem item) {
        //将数据类设置给布局文件
        dataBinding.setItem(item);
        //如果布局里还有其它逻辑，如颜色判断，隐藏显示，点击事件等，可用： dataBinding.xxx  xxx代表布局里控件的ID
//            dataBinding.tvContent.setTextColor(getContext().getResources().getColor(R.color.teal_700));
        //如果用到context 则使用   getContext()
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.demo_item_layout;
    }
}
