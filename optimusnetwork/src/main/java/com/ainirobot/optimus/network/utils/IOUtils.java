package com.ainirobot.optimus.network.utils;

import android.util.Log;

import java.io.Closeable;
import java.io.IOException;

/**
 * @filename: IOUtils
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2019/4/13 13:23
 * Copyright ©2018 Frewen.Wong. All Rights Reserved.
 */
public class IOUtils {
    private static final String TAG = "IOUtils";

    /**
     * 关闭所有的输入输出流
     *
     * @param closeables
     */
    public static void closeAll(Closeable... closeables) {
        if (null == closeables || closeables.length <= 0) {
            return;
        }
        //遍历所有的可变参数。进行关闭流
        for (Closeable cb : closeables) {
            close(cb);
        }
    }

    /**
     * 关闭当前的输入输出流
     *
     * @param closeable
     */
    public static void close(Closeable closeable) {
        if (null == closeable) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            Log.e(TAG, "an error occur when close Closeable:" + closeable.toString());
            throw new RuntimeException(TAG, e);
        }
    }
}
