package com.ainirobot.optimus.network.utils;

/**
 * @filename: HttpUtils
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2019/4/13 13:02
 * Copyright ©2018 Frewen.Wong. All Rights Reserved.
 */
public class HttpUtils {
    private static final int HTTPS_LEN = 5;
    private static final String HTTPS = "https";

    public static boolean connectionIsHttps(String url) {
        if (url.regionMatches(0, HTTPS, 0, HTTPS_LEN)) {
            return true;
        } else {
            return false;
        }
    }
}
