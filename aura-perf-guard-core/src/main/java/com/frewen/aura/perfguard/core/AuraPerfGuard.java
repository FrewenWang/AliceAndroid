package com.frewen.aura.perfguard.core;

import android.app.admin.UnsafeStateException;
import android.content.Context;
import android.util.Log;

import com.frewen.aura.perfguard.core.engine.IPerfGuardEngine;
import com.frewen.aura.perfguard.core.engine.cpu.CPUEngine;
import com.frewen.aura.perfguard.core.engine.cpu.CpuConfig;
import com.frewen.aura.perfguard.core.engine.startup.StartupInfo;
import com.frewen.aura.toolkits.exceptions.UnInstallException;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import androidx.annotation.MainThread;
import androidx.annotation.StringDef;

/**
 * @filename: PerfGuard
 * @author: Frewen.Wong
 * @time: 2021/8/12 08:08
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
public class AuraPerfGuard {
    private static final String TAG = "AuraPerfGuard";
    private Map<String, IPerfGuardEngine<?>> mEngineModules = new HashMap<>();
    private static final int DEFAULT_PORT = 5390;
    private PerfGuardConfig mConfig;
    private Context context;

    private AuraPerfGuard() {
    }

    private static class SingleHolder {
        private static final AuraPerfGuard sInstance = new AuraPerfGuard();
    }

    public static AuraPerfGuard instance() {
        return SingleHolder.sInstance;
    }

    public AuraPerfGuard init(Context context, PerfGuardConfig config) {
        long startTime = System.currentTimeMillis();
        this.mConfig = config;
        this.context = context.getApplicationContext();
        // 初始化CPU性能监控引擎
        if (config.getCpuConfig() != null) {
            IPerfGuardEngine<?> engine = mEngineModules.get(ModuleName.CPU);
            if (null == engine) {
                engine = new CPUEngine();
            }
            ((CPUEngine) engine).init(config.getCpuConfig());
        }
        Log.d(TAG, String.format("PerfGuard All Engines initialized, cost %s ms, config: %s"
                , (System.currentTimeMillis() - startTime), config));
        return this;
    }

    public synchronized @ModuleName
    Set<String> getEngineModules() {
        return mEngineModules.keySet();
    }

    public synchronized IPerfGuardEngine getModule(@ModuleName String moduleName) {
        IPerfGuardEngine<?> engine = mEngineModules.get(moduleName);
        if (engine == null) {
            throw new IllegalArgumentException("engineModule [" + moduleName + "] is not installed.");
        }
        return engine;
    }

    @MainThread
    public void startMonitor() {
        startMonitor(DEFAULT_PORT);
    }

    /**
     *
     */
    @MainThread
    public void startMonitor(int port) {
        if (null != this.mConfig.getGuardMonitor()) {
            this.mConfig.getGuardMonitor().starMonitor(context, port);
        }
    }

    /**
     *
     */
    @MainThread
    public void stopMonitor() {
        if (null != this.mConfig.getGuardMonitor()) {
            this.mConfig.getGuardMonitor().stopMonitor();
        }
    }

    /**
     * 应用启动完成之后调用此方法，用于记录应用的启动时长
     *
     * @param startupInfo
     *
     * @throws UnInstallException
     */
    public void onAppStartEnd(StartupInfo startupInfo) throws UnInstallException {

    }

    /**
     *
     */
    public synchronized AuraPerfGuard destroy() {
        long startTime = System.currentTimeMillis();
        for (Map.Entry<String, IPerfGuardEngine<?>> entry : mEngineModules.entrySet()) {
            entry.getValue().destroy();
        }
        mEngineModules.clear();
        return this;
    }


    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ModuleName.CPU
    })
    public @interface ModuleName {
        public static final String CPU = "CPU";
    }

}
