package com.frewen.aura.perfguard.core.engine.cpu;

import com.frewen.aura.perfguard.core.base.IEngineConfig;

/**
 * @filename: CpuConfig
 * @author: Frewen.Wong
 * @time: 2021/8/12 08:23
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
public class CpuConfig implements IEngineConfig {
    /**
     * 需要同级的进程ID
     */
    public int pId = android.os.Process.myPid();
    /**
     * Dump CPU使用信息的间隔时间
     */
    public long intervalMillis;

    public CpuConfig(long intervalMillis) {
        this.intervalMillis = intervalMillis;
    }

    public CpuConfig() {
        this.intervalMillis = 2000;
    }

    public long intervalMillis() {
        return intervalMillis;
    }

    @Override
    public String toString() {
        return "CpuConfig{" +
                "intervalMillis=" + intervalMillis +
                '}';
    }
}
