package com.frewen.aura.logger.core;

import android.util.Printer;

import com.frewen.aura.logger.printer.IPrinter;
import com.frewen.aura.logger.printer.LogPrinter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @filename: AuraLogger
 * @author: Frewen.Wong
 * @time: 2021/8/11 08:36
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
public class AuraLogger {
    @NonNull
    private static IPrinter printer = new LogPrinter();

    public static void d(@Nullable Object object) {
        printer.d(object);
    }

    public static void d(@NonNull String message, @Nullable Object... args) {
        printer.d(message, args);
    }

    public static void i(@Nullable Object object) {
        printer.i(object);
    }


    public static void i(@NonNull String message, @Nullable Object... args) {
        printer.i(message, args);
    }


    public static void e(@NonNull String message, @Nullable Object... args) {
        printer.e(null, message, args);
    }

    public static void e(@Nullable Throwable throwable, @NonNull String message, @Nullable Object... args) {
        printer.e(throwable, message, args);
    }

    public static void v(@NonNull String message, @Nullable Object... args) {
        printer.v(message, args);
    }

    public static void w(@NonNull String message, @Nullable Object... args) {
        printer.w(message, args);
    }

    /**
     * Tip: Use this for exceptional situations to log
     * ie: Unexpected errors etc
     */
    public static void wtf(@NonNull String message, @Nullable Object... args) {
        printer.wtf(message, args);
    }

    /**
     * Formats the given json content and print it
     */
    public static void json(@Nullable String json) {
        printer.json(json);
    }

    /**
     * Formats the given xml content and print it
     */
    public static void xml(@Nullable String xml) {
        printer.xml(xml);
    }


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({LogLevel.ALL, LogLevel.VERBOSE, LogLevel.DEBUG, LogLevel.INFO,
            LogLevel.WARN, LogLevel.ERROR, LogLevel.ASSERT, LogLevel.NONE
    })

    public @interface LogLevel {
        public static final int ALL = 0;
        public static final int VERBOSE = 1;
        public static final int DEBUG = 2;
        public static final int INFO = 3;
        public static final int WARN = 4;
        public static final int ERROR = 5;
        public static final int ASSERT = 6;
        public static final int NONE = 7;
    }


}
