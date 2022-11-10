package com.huodada.lib_common.router;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 路由跳转
 * created by yhw
 * date 2022/11/10
 */
public class RouterUtils {

    /**
     * 跳转到demo
     */
    public static void jumpDemo() {
        ARouter.getInstance().build(RouterPath.DEMO_ACTIVITY).navigation();
    }

    /**
     * 跳转到浏览器
     */
    public static void jumpWeb(String url) {
        ARouter.getInstance().build(RouterPath.WEB_ACTIVITY)
                .withString("url", url)
                .navigation();
    }
}
