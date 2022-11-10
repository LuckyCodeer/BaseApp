package com.huodada.lib_common.http;

import rxhttp.wrapper.annotation.DefaultDomain;

/**
 * created by yhw
 * date 2022/11/8
 */
public class Api {
    //默认baseUrl
    @DefaultDomain
    public static String baseUrl = "https://www.wanandroid.com/";

    /**
     * XX接口
     */
    public static String FRIEND = "friend/json";
}
