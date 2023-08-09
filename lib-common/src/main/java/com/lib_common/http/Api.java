package com.lib_common.http;

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
    /**
     * 项目列表
     */
    public static String PROJECT_LIST = "project/list";
}
