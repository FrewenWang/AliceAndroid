package com.frewen.demo.library.utils;

import androidx.annotation.NonNull;

import android.os.Build;
import android.os.Trace;

import androidx.core.os.TraceCompat;

public class TraceUtil {

    public static void beginSection(@NonNull String sectionName) {
        // 使用SysTrace进行分析
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Trace.beginSection(sectionName);
        } else {
            TraceCompat.beginSection(sectionName);
        }
    }

    public static void endSection() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Trace.endSection();
        } else {
            TraceCompat.endSection();
        }
    }

}
