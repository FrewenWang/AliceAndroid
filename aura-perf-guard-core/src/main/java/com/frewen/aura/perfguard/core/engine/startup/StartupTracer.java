package com.frewen.aura.perfguard.core.engine.startup;

import android.app.Activity;
import android.os.Looper;

/**
 * @filename: StartupTracer
 * @author: Frewen.Wong
 * @time: 2022/3/1 23:41
 * @version: 1.0.0
 * @introduction: 应用启动的Trace信息记录器，用于记录启动耗时
 * @copyright: Copyright ©2022 Frewen.Wong. All Rights Reserved.
 */
public class StartupTracer {

    private long mAppStartTime;
    private long mSplashStartTime;

    private static class InstanceHolder {
        private static final StartupTracer sInstance = new StartupTracer();
    }

    public static StartupTracer instance() {
        return InstanceHolder.sInstance;
    }

    public void onApplicationCreate() {
        mAppStartTime = System.currentTimeMillis();
    }

    public void onSplashCreate() {
        mSplashStartTime = System.currentTimeMillis();
    }

    public void onHomeCreate(Activity activity) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("you should invoke onHomeCreate ui thread!");
        }

        activity.getWindow().getDecorView().post(() -> {
            
        });
    }

    private StartupInfo generateStartupInfo(long homeEndTime) {
        if (isCodeStart(homeEndTime)) {
            return new StartupInfo(StartupInfo.StartupType.COLD, homeEndTime - mAppStartTime);
        } else if (isHotStart(homeEndTime)) {
            return null;
        }
        return null;
    }

    private boolean isCodeStart(long homeEndTime) {
        return mAppStartTime > 0 && homeEndTime > mSplashStartTime && mSplashStartTime > mAppStartTime;
    }

    private boolean isHotStart(long homeEndTime) {
        return mAppStartTime <= 0 && mSplashStartTime > 0 && homeEndTime > mSplashStartTime;
    }

}
