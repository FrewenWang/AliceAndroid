package com.frewen.aura.logger.printer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @filename: Printer
 * @author: Frewen.Wong
 * @time: 2021/8/14 10:18
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
public interface IPrinter {

    void d(@Nullable Object object);

    void d(@NonNull String message, @Nullable Object... args);

    void i(@Nullable Object object);

    void i(@NonNull String message, @Nullable Object... args);

    void e(@NonNull String message, @Nullable Object... args);

    void e(@Nullable Throwable throwable, @NonNull String message, @Nullable Object... args);

    void w(@NonNull String message, @Nullable Object... args);


    void v(@NonNull String message, @Nullable Object... args);

    void wtf(@NonNull String message, @Nullable Object... args);

    void json(@Nullable String json);

    /**
     * Formats the given xml content and print it
     */
    void xml(@Nullable String xml);

    void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable);
}


