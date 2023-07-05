package com.lib_common.utils;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.lib_src.R;

import java.util.List;

/**
 * 权限请求工具
 * created by yhw
 * date 2022/11/10
 */
public class PermissionUtil {
    public static void requestPermission(Context context, String... permissions) {
        requestPermission(context, null, permissions);
    }

    /**
     * 发起多权限请求
     *
     * @param context            上下文
     * @param permissions        权限数组
     * @param permissionCallback 回调
     */
    public static void requestPermission(Context context, String[] permissions, PermissionCallback permissionCallback) {
        requestPermission(context, permissionCallback, true, permissions);
    }

    /**
     * 发起单权限请求
     *
     * @param context            上下文
     * @param permissions        单个权限
     * @param permissionCallback 回调
     */
    public static void requestPermission(Context context, String permissions, PermissionCallback permissionCallback) {
        requestPermission(context, permissionCallback, true, permissions);
    }

    /**
     * 单个或者多个权限请求
     *
     * @param context            上下文
     * @param permissionCallback 回调
     * @param permissions        权限-不定数组
     */
    public static void requestPermission(Context context, PermissionCallback permissionCallback, String... permissions) {
        requestPermission(context, permissionCallback, true, permissions);
    }

    /**
     * 单个或者多个权限请求
     *
     * @param context            上下文
     * @param permissionCallback 权限回调
     * @param isShowHint         是否显示拒绝后的提示
     * @param permissions        权限
     */
    public static void requestPermission(Context context, PermissionCallback permissionCallback, boolean isShowHint, String... permissions) {
        XXPermissions.with(context).permission(permissions).request(new OnPermissionCallback() {
            @Override
            public void onGranted(List<String> list, boolean all) {
                if (permissionCallback == null) {
                    return;
                }
                if (all) {
                    permissionCallback.onGranted();
                } else {
                    permissionCallback.onDenied();
                }
            }

            @Override
            public void onDenied(List<String> permissions, boolean never) {
                if (permissionCallback == null) {
                    return;
                }
                permissionCallback.onDenied();
                denied(context, permissions, never, isShowHint);
            }
        });
    }

    /**
     * 是否有某个权限
     */
    public static boolean isGranted(Context context, String... permissions) {
        return XXPermissions.isGranted(context, permissions);
    }

    /**
     * 是否有某个权限
     */
    public static boolean isGranted(Context context, String[]... permissions) {
        return XXPermissions.isGranted(context, permissions);
    }

    /**
     * 授权结果回调
     */
    public interface PermissionCallback {
        //全部允许
        void onGranted();

        //部分拒绝或者全部拒绝
        default void onDenied() {
        }
    }

    /**
     * 拒绝后的处理逻辑
     *
     * @param never 是否永久拒绝
     */
    private static void denied(Context context, List<String> permissions, boolean never, boolean isShowHint) {
        if (!isShowHint) {
            return;
        }
        if (permissions == null || permissions.size() == 0) {
            return;
        }
        String permissionName = "相关";
        if (permissions.contains(Permission.WRITE_EXTERNAL_STORAGE) || permissions.contains(Permission.READ_EXTERNAL_STORAGE)) {
            permissionName = "读写手机存储";
        } else if (permissions.contains(Permission.CAMERA)) {
            permissionName = "相机";
        } else if (permissions.contains(Permission.SEND_SMS)) {
            permissionName = "发生短信";
        } else if (permissions.contains(Permission.CALL_PHONE)) {
            permissionName = "拨打电话";
        } else if (permissions.contains(Permission.PROCESS_OUTGOING_CALLS)) {
            permissionName = "电话状态(接听或挂断电话)";
        } else if (permissions.contains(Permission.WRITE_CONTACTS) || permissions.contains(Permission.READ_CONTACTS)) {
            permissionName = "读写联系人";
        } else if (permissions.contains(Permission.READ_CALL_LOG)) {
            permissionName = "读取通话记录";
        } else if (permissions.contains(Permission.ACCESS_COARSE_LOCATION) || permissions.contains(Permission.ACCESS_FINE_LOCATION)) {
            permissionName = "定位";
        } else if (permissions.contains(Permission.READ_CALENDAR) || permissions.contains(Permission.WRITE_CALENDAR)) {
            permissionName = "访问日历";
        } else if (permissions.contains(Permission.NOTIFICATION_SERVICE)) {
            permissionName = "通知";
        } else if (permissions.contains(Permission.RECORD_AUDIO)) {
            permissionName = "录音或麦克风";
        }
        if (never) {
            new AlertDialog.Builder(context)
                    .setTitle(R.string.dialog_title)
                    .setMessage("应用 " + permissionName + " 权限被拒绝授权，请手动授予该权限，否则该功能无法正常使用")
                    .setPositiveButton(R.string.go_setting, (dialogInterface, i) -> {
                        // 如果是被永久拒绝就跳转到应用权限系统设置页面
                        XXPermissions.startPermissionActivity(context, permissions);
                    })
                    .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {

                    })
                    .show();
        } else {
            ToastUtils.show("请授予应用 " + permissionName + " 权限，否则该功能无法正常使用");
        }
    }

    /**
     * 跳转到权限设置界面
     */
    public static void startPermissionActivity(final Context context) {
        XXPermissions.startPermissionActivity(context);
    }
}
