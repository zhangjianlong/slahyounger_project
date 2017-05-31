package com.core.op.lib.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by acer on 2017/3/31.
 */

public class DoubleUtil {

    /**
     * 修改精度
     *
     * @param value
     * @param num
     * @return
     */
    public static double changeDecimal(double value, int num) {
        BigDecimal b = new BigDecimal(value);
        double v = b.setScale(num, 4).doubleValue();
        return v;
    }

    /**
     * 两个double相加方法
     *
     * @param a
     * @param b
     * @return
     */
    public static Double doubleAdd(Double a, Double b) {
        BigDecimal b1 = new BigDecimal(Double.toString(a));
        BigDecimal b2 = new BigDecimal(Double.toString(b));
        return b1.add(b2).doubleValue();
    }

    /**
     * 两个double相加方法,并保留指定精度
     *
     * @param a
     * @param b
     * @param num
     * @return
     */
    public static Double doubleAdd(Double a, Double b, int num) {
        return changeDecimal(doubleAdd(a, b), num);
    }

    /**
     * 两个double相减方法
     *
     * @param a
     * @param b
     * @return
     */
    public static Double doubleSub(Double a, Double b) {
        BigDecimal b1 = new BigDecimal(Double.toString(a));
        BigDecimal b2 = new BigDecimal(Double.toString(b));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 两个double相减方法,并保留指定精度
     *
     * @param a
     * @param b
     * @param num
     * @return
     */
    public static Double doubleSub(Double a, Double b, int num) {
        return changeDecimal(doubleSub(a, b), num);
    }

    /**
     * 两个double相乘方法
     *
     * @param a
     * @param b
     * @return
     */
    public static Double doubleMul(Double a, Double b) {
        BigDecimal b1 = new BigDecimal(Double.toString(a));
        BigDecimal b2 = new BigDecimal(Double.toString(b));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 两个double相乘方法,并保留指定精度
     *
     * @param a
     * @param b
     * @param num
     * @return
     */
    public static Double doubleMul(Double a, Double b, int num) {
        return changeDecimal(doubleMul(a, b), num);
    }

    /**
     * 两个double相除方法,并保留指定精度
     *
     * @param a
     * @param b
     * @param scale
     * @return
     */
    public static Double doubleDiv(Double a, Double b, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(a));
        BigDecimal b2 = new BigDecimal(Double.toString(b));
        return Double.valueOf(b1.divide(b2, scale, 4).doubleValue());
    }
}
