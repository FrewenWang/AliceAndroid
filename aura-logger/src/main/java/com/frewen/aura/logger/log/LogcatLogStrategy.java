package com.frewen.aura.logger.log;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.frewen.aura.toolkits.utils.Preconditions;

public class LogcatLogStrategy implements LogStrategy {

    static final String DEFAULT_TAG = "NO_TAG";

    @Override
    public void log(int priority, @Nullable String tag, @NonNull String message) {
        Preconditions.notNull(message);

        if (tag == null) {
            tag = DEFAULT_TAG;
        }

        Log.println(priority, tag, message);
    }
}
