package com.frewen.aura.logger.proxy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @filename: AndroidLogProxy
 * @author: Frewen.Wong
 * @time: 2021/8/14 14:12
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
public class AndroidLogProxy implements LogProxy {

    @Override
    public boolean logEnable(int level, @Nullable String tag) {
        return false;
    }

    @Override
    public void log(int level, @Nullable String tag, @NonNull String message) {

    }
}
