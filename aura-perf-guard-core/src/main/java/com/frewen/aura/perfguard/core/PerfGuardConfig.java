package com.frewen.aura.perfguard.core;

import com.frewen.aura.perfguard.core.engine.cpu.CpuConfig;
import com.frewen.aura.perfguard.core.monitor.IPerfGuardMonitor;

import java.io.Serializable;

/**
 * @filename: PerfGuardConfig
 * @author: Frewen.Wong
 * @time: 2021/8/12 08:15
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
public class PerfGuardConfig implements Serializable {

    private CpuConfig cpuConfig;

    private IPerfGuardMonitor mGuardMonitor;

    public IPerfGuardMonitor getGuardMonitor() {
        return this.mGuardMonitor;
    }

    public CpuConfig getCpuConfig() {
        return cpuConfig;
    }

    public static PerfGuardConfig newConfig() {
        return Builder.newBuilder().build();
    }


    /**
     * PerfGuardConfig Builder 建造者
     */
    public static final class Builder {

        private CpuConfig cpuConfig = new CpuConfig();
        public IPerfGuardMonitor mGuardMonitor;

        public Builder setCpuConfig(CpuConfig cpuConfig) {
            this.cpuConfig = cpuConfig;
            return this;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public PerfGuardConfig build() {
            PerfGuardConfig perfGuardConfig = new PerfGuardConfig();
            perfGuardConfig.cpuConfig = this.cpuConfig;
            perfGuardConfig.mGuardMonitor = this.mGuardMonitor;
            return perfGuardConfig;
        }
    }
}
