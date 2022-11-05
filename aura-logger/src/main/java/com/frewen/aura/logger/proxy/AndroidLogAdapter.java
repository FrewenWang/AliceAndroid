package com.frewen.aura.logger.proxy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.frewen.aura.logger.format.IFormatStrategy;
import com.frewen.aura.logger.format.PrettyFormatStrategy;
import com.frewen.aura.toolkits.utils.Preconditions;

/**
 * @filename: AndroidLogProxy
 * @author: Frewen.Wong
 * @time: 2021/8/14 14:12
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
public class AndroidLogAdapter implements ILogAdapter {

    @NonNull
    private final IFormatStrategy formatStrategy;

    public AndroidLogAdapter() {
        this.formatStrategy = PrettyFormatStrategy.newBuilder().build();
    }

    public AndroidLogAdapter(@NonNull IFormatStrategy formatStrategy) {
        this.formatStrategy = Preconditions.notNull(formatStrategy);
    }

    @Override
    public boolean logEnable(int level, @Nullable String tag) {
        return false;
    }

    @Override
    public void log(int level, @Nullable String tag, @NonNull String message) {
        formatStrategy.log(level, tag, message);
    }
}
