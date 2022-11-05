package com.frewen.aura.logger.log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface LogStrategy {

    void log(int priority, @Nullable String tag, @NonNull String message);
}
