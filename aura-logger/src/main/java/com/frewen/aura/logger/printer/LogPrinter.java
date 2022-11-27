package com.frewen.aura.logger.printer;

import android.annotation.SuppressLint;

import com.frewen.aura.logger.utils.CommonUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static androidx.core.util.Preconditions.checkNotNull;
import static com.frewen.aura.logger.core.ALogger.LogLevel.ASSERT;
import static com.frewen.aura.logger.core.ALogger.LogLevel.DEBUG;
import static com.frewen.aura.logger.core.ALogger.LogLevel.ERROR;
import static com.frewen.aura.logger.core.ALogger.LogLevel.INFO;
import static com.frewen.aura.logger.core.ALogger.LogLevel.VERBOSE;
import static com.frewen.aura.logger.core.ALogger.LogLevel.WARN;


public class LogPrinter implements IPrinter {

    private final ThreadLocal<String> localTag = new ThreadLocal<>();

    @Override
    public void d(@Nullable Object object) {
        log(DEBUG, null, CommonUtils.toString(object));
    }

    @Override
    public void d(@NonNull String message, @Nullable Object... args) {
        log(DEBUG, null, message, args);
    }

    @Override
    public void i(@Nullable Object object) {
        log(INFO, null, CommonUtils.toString(object));
    }

    @Override
    public void i(@NonNull String message, @Nullable Object... args) {
        log(INFO, null, message, args);
    }


    @Override
    public void e(@NonNull String message, @Nullable Object... args) {
        e(null, message, args);
    }

    @Override
    public void e(@Nullable Throwable throwable, @NonNull String message, @Nullable Object... args) {
        log(ERROR, throwable, message, args);
    }

    @Override
    public void w(@NonNull String message, @Nullable Object... args) {
        log(WARN, null, message, args);
    }


    @Override
    public void v(@NonNull String message, @Nullable Object... args) {
        log(VERBOSE, null, message, args);
    }

    @Override
    public void wtf(@NonNull String message, @Nullable Object... args) {
        log(ASSERT, null, message, args);
    }

    @Override
    public void json(@Nullable String json) {

    }

    @Override
    public void xml(@Nullable String xml) {

    }

    @Override
    public synchronized void log(int priority,
                                 @Nullable String tag,
                                 @Nullable String message,
                                 @Nullable Throwable throwable) {
        // if (throwable != null && message != null) {
        //     message += " : " + Utils.getStackTraceString(throwable);
        // }
        // if (throwable != null && message == null) {
        //     message = Utils.getStackTraceString(throwable);
        // }
        // if (Utils.isEmpty(message)) {
        //     message = "Empty/NULL log message";
        // }
        //
        // for (LogAdapter adapter : logAdapters) {
        //     if (adapter.isLoggable(priority, tag)) {
        //         adapter.log(priority, tag, message);
        //     }
        // }
    }


    /**
     * This method is synchronized in order to avoid messy of logs' order.
     */
    @SuppressLint("RestrictedApi")
    private synchronized void log(int priority,
                                  @Nullable Throwable throwable,
                                  @NonNull String msg,
                                  @Nullable Object... args) {
        checkNotNull(msg);
        String tag = getTag();
        String message = createMessage(msg, args);
        log(priority, tag, message, throwable);
    }

    /**
     * @return the appropriate tag based on local or global
     */
    @Nullable
    private String getTag() {
        String tag = localTag.get();
        if (tag != null) {
            localTag.remove();
            return tag;
        }
        return null;
    }

    @NonNull
    private String createMessage(@NonNull String message, @Nullable Object... args) {
        return args == null || args.length == 0 ? message : String.format(message, args);
    }
}
