package com.lib_common.utils;

/**
 * created by yhw
 * date 2024/11/1
 */
public class TimeUtils {

    /**
     * 格式化倒计时时间
     *
     * @param time 时长，单位秒
     */
    public static String formatCountDownTime(long time) {
        int hh = (int) (time / 60 / 60 % 60);
        int mm = (int) (time / 60 % 60);
        int ss = (int) (time % 60);
        if (time / 60 / 60 >= 1) {
            return hh + "时" + mm + "分" + ss + "秒";
        } else if (time / 60 >= 1) {
            return mm + "分" + ss + "秒";
        } else {
            return ss + "秒";
        }
    }

    /**
     * 格式化分,不足两位的补0
     *
     * @param time 时长，单位秒
     */
    public static String formatMinute(long time) {
        int mm = (int) (time / 60 % 60);
        if (mm == 0) {
            return "";
        }
        return (mm < 10 ? "0" + mm : String.valueOf(mm));
    }

    /**
     * 格式化秒,不足两位的补0
     *
     * @param time 时长，单位秒
     */
    public static String formatSecond(long time) {
        int ss = (int) (time % 60);
        if (ss == 0) {
            return "00";
        }
        return (ss < 10 ? "0" + ss : String.valueOf(ss));
    }
}
