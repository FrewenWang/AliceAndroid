package com.frewen.android.demo.utils;

/**
 * @filename: StringConvert
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/7 19:35
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class StringConvert {

    public static String convertFeedUgc(int count) {
        if (count < 10000) {
            return String.valueOf(count);
        }
        return count / 10000 + "万";
    }
}
