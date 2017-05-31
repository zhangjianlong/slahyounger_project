package com.slash.youth.utils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.amap.loc.a.r;

public class StringUtils {
    /**
     * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
     */
    public static boolean isEmpty(String value) {
        if (value != null && !"".equalsIgnoreCase(value.trim())
                && !"null".equalsIgnoreCase(value.trim())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    //判断是否是中英文
    public static boolean isSearchContent(String text) {
        String check = "^[\\u4E00-\\u9FA5A-Za-z]+$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(text);

        return matcher.matches();
    }

    //纯数字 "[0-9]+"
    public static boolean isSearchNumberContent(String text) {
        String check = "[0-9]+";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(text);
        return matcher.matches();
    }

    public static String getLongToDate(long lo) {
        Date date = new Date(lo);
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        return sd.format(date);
    }

    //手机号码格式变为139****4532
    public static String phoneFormat(String tempStr) {
        if (StringUtils.isEmpty(tempStr)) {
            return "****";
        }
        StringBuffer stringBuffer = new StringBuffer();
        String first = "";
        String last = "";

        if (tempStr.length() >= 11) {
            first = tempStr.substring(0, 3);
            last = tempStr.substring(tempStr.length() - 4, tempStr.length());
            stringBuffer.append(first).append("****").append(last);
        } else {
            first = tempStr.substring(0, 1);
            last = tempStr.substring(tempStr.length() - 1, tempStr.length());
            stringBuffer.append(first).append("****").append(last);

        }
        return stringBuffer.toString();
    }

    //分割技能标签，返回的格式为**-**-****
    public static String strFormat(String tempStr) {

        // 最多分割出3个字符串
        int maxSplit = 3;
        if (tempStr.contains("-")) {
            String[] sourceStrArray = tempStr.split("-", maxSplit);
            return sourceStrArray[sourceStrArray.length - 1];
        }
        return tempStr;
    }



    //检查金额是否是小数 是否为0开头
    public static boolean checkMonkey(String tempStr) {
        if (StringUtils.isEmpty(tempStr)) {
            ToastUtils.shortToast("请填写金额");
            return false;
        }

        if (tempStr.contains(".")) {
            ToastUtils.shortToast("金额不能为小数");
            return false;
        }
        if (tempStr.startsWith("0")) {
            ToastUtils.shortToast("金额不能为0开头");
            return false;

        }
        return true;
    }

}
