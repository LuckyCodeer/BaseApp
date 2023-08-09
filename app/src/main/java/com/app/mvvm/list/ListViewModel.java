package com.app.mvvm.list;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.hjq.toast.ToastUtils;
import com.lib_common.base.mvvm.BaseViewModel;
import com.lib_common.entity.ProjectResponse;
import com.lib_common.http.HttpListener;
import com.lib_common.http.HttpUtils;

/**
 * created by yhw
 * date 2023/8/9
 */
public class ListViewModel extends BaseViewModel {
    private final MutableLiveData<ProjectResponse> mListData = new MutableLiveData<>();

    public ListViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ProjectResponse> getListData() {
        return mListData;
    }

    /**
     * 获取数据
     */
    public void requestData(Context context, int pageNum) {
        showLoading();
        HttpUtils.getProjectList((LifecycleOwner) context, pageNum, new HttpListener<ProjectResponse>() {
            @Override
            public void onSuccess(ProjectResponse projectResponse) {
                dismissLoading();
                mListData.postValue(projectResponse);
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoading();
                ToastUtils.show(errorMsg);
            }
        });
    }
}
