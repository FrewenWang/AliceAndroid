package com.frewen.network.utils;

import android.text.TextUtils;

import java.util.Locale;

/**
 * @filename: HttpUtils
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2019/4/15 0015 下午5:33
 * Copyright ©2019 Frewen.Wong. All Rights Reserved.
 */
public class HttpUtils {

    private static String acceptLanguage;
    /**
     * Accept-Language: zh-CN,zh;q=0.8
     */
    public static String getAcceptLanguage() {
        if (TextUtils.isEmpty(acceptLanguage)) {
            Locale locale = Locale.getDefault();
            String language = locale.getLanguage();
            String country = locale.getCountry();
            StringBuilder acceptLanguageBuilder = new StringBuilder(language);
            if (!TextUtils.isEmpty(country))
                acceptLanguageBuilder.append('-').append(country).append(',').append(language).append(";q=0.8");
            acceptLanguage = acceptLanguageBuilder.toString();
            return acceptLanguage;
        }
        return acceptLanguage;
    }
}
