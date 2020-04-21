package com.frewen.network.utils;


import androidx.annotation.Nullable;

/**
 * @filename: CommonUtils
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2019/4/14 23:14
 * Copyright ©2018 Frewen.Wong. All Rights Reserved.
 */
public class CommonUtils {

    public static <T> T checkNotNull(@Nullable T object) {
        return checkNotNull(object, "object is null");
    }

    public static <T> T checkNotNull(@Nullable T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }
}
