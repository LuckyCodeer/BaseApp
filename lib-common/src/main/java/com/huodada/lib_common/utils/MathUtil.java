package com.huodada.lib_common.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 有关数学运算的工具，如加减乘除，四舍五入等
 */
public class MathUtil {

    /**
     * 获取小数位数
     */
    public static int getDecimalDigits(String num) {
        if (TextUtils.isEmpty(num) || Double.parseDouble(num) == 0) {
            return 0;
        }
        final String result = new BigDecimal(num).stripTrailingZeros().toPlainString();
        if (!result.contains(".")) {
            return 0;
        }
        return result.codePointCount(result.lastIndexOf(".") + 1, result.length());
    }

    /**
     * 保留2位小数 四舍五入
     */
    public static String getKeepDecimalDigits(double num) {
        return getKeepDecimalDigits(num, 2);
    }

    /**
     * 保留2位小数 四舍五入
     *
     * @param num 数字
     */
    public static String getKeepDecimalDigits(String num) {
        return getKeepDecimalDigits(Double.parseDouble(num), 2);
    }

    /**
     * 保留小数位数 四舍五入
     *
     * @param num     数字
     * @param decimal 小数位数
     */
    public static String getKeepDecimalDigits(String num, int decimal) {
        return getKeepDecimalDigits(Double.parseDouble(num), decimal);
    }

    /**
     * 保留小数位数 四舍五入
     *
     * @param num     数字
     * @param decimal 小数位数
     */
    public static String getKeepDecimalDigits(double num, int decimal) {
        BigDecimal bigDecimal = new BigDecimal(num);
        return bigDecimal.setScale(decimal, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    /**
     * 保留两位小数 不四舍五入
     *
     * @param num 数字
     * @return 两位小数
     */
    public static String getKeepDecimalDigitsNotRound(String num) {
        if (TextUtils.isEmpty(num)) {
            return "0";
        }
        return getKeepDecimalDigitsNotRound(Double.parseDouble(num));
    }

    /**
     * 保留两位小数 不四舍五入
     *
     * @param num 数字
     * @return 两位小数
     */
    public static String getKeepDecimalDigitsNotRound(double num) {
        return getKeepDecimalDigitsNotRound(num, 2);
    }

    /**
     * 保留小数位数 不四舍五入
     *
     * @param num     数字
     * @param decimal 小数位数
     */
    public static String getKeepDecimalDigitsNotRound(double num, int decimal) {
        StringBuilder format = new StringBuilder("0.");
        if (decimal <= 0) {
            format = new StringBuilder("0");
        } else {
            for (int i = 0; i < decimal; i++) {
                format.append("0");
            }
        }
        DecimalFormat df = new DecimalFormat(format.toString());
        return df.format(num);
    }

    /**
     * 是否为0
     */
    public static boolean isZero(String num) {
        if (TextUtils.isEmpty(num)) {
            return false;
        }
        try {
            return isZero(Double.parseDouble(num));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 是否为0
     */
    public static boolean isZero(double num) {
        return num == 0;
    }

    /**
     * 格式化数字
     * 当数值大于等于10000时（单位不在为个，显示1万）并保留2位小数，不四舍五入
     * 例如 10157 展示 1.01万
     * 当数值大于等于100000000时（显示1亿）并保留2位小数，不四舍五入
     * 例如311570150 展示为3.11亿
     *
     * @param num 传入的数值
     * @return 返回格式化的文本
     */
    public static String formatNum(double num) {
        try {
            final DecimalFormat numberFormat = new DecimalFormat("#.##");
            numberFormat.setRoundingMode(RoundingMode.DOWN);
            if (num < 10000) {
                return new BigDecimal(numberFormat.format(num)).stripTrailingZeros().toPlainString();
            }
            if (num < 100000000) {
                final double value = num / 10000d;
                return new BigDecimal(numberFormat.format(value)).stripTrailingZeros().toPlainString() + "万";
            }
            final double value = num / 100000000d;
            return new BigDecimal(numberFormat.format(value)).stripTrailingZeros().toPlainString() + "亿";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(num);
    }

    /**
     * 格式化长度
     *
     * @param length 长度 米
     */
    public static String formatLength(double length) {
        if (length < 1000) {
            return getKeepDecimalDigits(length, 0) + "m";
        }
        return getKeepDecimalDigits(length / 1000, 1) + "km";
    }

    /**
     * 格式化数字
     */
    public static String formatNum(String num) {
        return formatNum(Double.parseDouble(num));
    }

    /**
     * 格式化数字
     */
    public static String formatNum(int num) {
        return formatNum(String.valueOf(num));
    }

    /**
     * 去除小数后面多余的0
     */
    public static String stripTrailingZeros(double num) {
        return stripTrailingZeros(String.valueOf(num));
    }

    /**
     * 去除小数后面多余的0
     */
    public static String stripTrailingZeros(String num) {
        if (TextUtils.isEmpty(num)) {
            return "0";
        }
        if (Double.parseDouble(num) == 0) {
            return "0";
        }
        BigDecimal bigDecimal = new BigDecimal(num);
        return bigDecimal.stripTrailingZeros().toPlainString();
    }

    /**
     * 加法
     *
     * @param values 值
     */
    public static String add(String... values) {
        if (values == null || values.length == 0) {
            return "0";
        }
        BigDecimal bigDecimal = new BigDecimal("0");
        for (String value : values) {
            if (TextUtils.isEmpty(value)) {
                value = "0";
            }
            bigDecimal = bigDecimal.add(new BigDecimal(value));
        }
        return bigDecimal.toPlainString();
    }

    /**
     * 乘法
     */
    public static String multi(String... values) {
        if (values == null || values.length == 0) {
            return "0";
        }
        BigDecimal bigDecimal = new BigDecimal("1");
        for (String value : values) {
            if (TextUtils.isEmpty(value)) {
                value = "0";
            }
            bigDecimal = bigDecimal.multiply(new BigDecimal(value));
        }
        return bigDecimal.toPlainString();
    }

    /**
     * 减法
     */
    public static String sub(String... values) {
        if (values == null || values.length == 0) {
            return "0";
        }
        if (TextUtils.isEmpty(values[0])) {
            values[0] = "0";
        }
        BigDecimal bigDecimal = new BigDecimal(values[0]);
        for (int i = 1; i < values.length; i++) {
            if (TextUtils.isEmpty(values[i])) {
                values[i] = "0";
            }
            bigDecimal = bigDecimal.subtract(new BigDecimal(values[i]));
        }
        return bigDecimal.toPlainString();
    }
}
