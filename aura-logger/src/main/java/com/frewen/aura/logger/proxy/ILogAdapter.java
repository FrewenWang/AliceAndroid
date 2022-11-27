package com.frewen.aura.logger.proxy;

import com.frewen.aura.logger.core.ALogger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @filename: LogProxy
 * @author: Frewen.Wong
 * @time: 2021/8/14 14:10
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
public interface ILogAdapter {

    boolean logEnable(@ALogger.LogLevel int level, @Nullable String tag);

    /**
     * @param level   is the log level e.g. DEBUG, WARNING
     * @param tag     is the given tag for the log message.
     * @param message is the given message for the log message.
     */
    void log(@ALogger.LogLevel int level, @Nullable String tag, @NonNull String message);
}
