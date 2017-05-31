package com.slash.youth.utils;

import android.annotation.TargetApi;
import android.content.Context;

import java.text.DecimalFormat;

/**
 * Created by zss on 2016/12/13.
 */
public class CountUtils {
    public static String DecimalFormat(double count) {
        DecimalFormat dFormat = new DecimalFormat("0.00");
        return dFormat.format(count) + "";
    }
}
