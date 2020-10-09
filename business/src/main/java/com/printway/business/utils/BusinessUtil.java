package com.printway.business.utils;

public class BusinessUtil {
    public static double calcStoreFee(double revenue) {
        return Math.round(revenue * 6.4) / 100.00;
    }
}
