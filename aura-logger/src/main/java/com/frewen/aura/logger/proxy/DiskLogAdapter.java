package com.frewen.aura.logger.proxy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DiskLogAdapter implements ILogAdapter {

    @Override
    public boolean logEnable(int level, @Nullable String tag) {
        return true;
    }

    @Override
    public void log(int level, @Nullable String tag, @NonNull String message) {

    }
}
