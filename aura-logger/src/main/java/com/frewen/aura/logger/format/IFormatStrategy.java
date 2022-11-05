package com.frewen.aura.logger.format;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Used to determine how messages should be printed or saved.
 *
 * @see PrettyFormatStrategy
 * @see CsvFormatStrategy
 */
public interface IFormatStrategy {

    void log(int priority, @Nullable String tag, @NonNull String message);
}
