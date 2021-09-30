package com.frewen.aura.perfgurad.monitor.server;

import android.util.Log;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * @filename: EngineModuleDataDriver
 * @author: Frewen.Wong
 * @time: 2021/8/20 07:23
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
public class EngineDataDriver {
    private static final String TAG = "EngineDataDriver";
    private boolean mIsRunning;
    private CompositeDisposable mCompositeDisposable;
    private IMessager mMessager;

    private static class InstanceHolder {
        private static EngineDataDriver sInstance = new EngineDataDriver();
    }

    public static EngineDataDriver instance() {
        return InstanceHolder.sInstance;
    }

    private EngineDataDriver() {

    }


    public void start(IMessager messager) {
        if (mIsRunning) {
            return;
        }
        mIsRunning = true;
        Log.d(TAG, "ModuleDriver start running.");
        mCompositeDisposable = new CompositeDisposable();
        mMessager = messager;
        mCompositeDisposable
    }
}
