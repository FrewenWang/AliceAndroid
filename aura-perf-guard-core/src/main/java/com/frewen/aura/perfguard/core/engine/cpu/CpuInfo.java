package com.frewen.aura.perfguard.core.engine.cpu;

import java.io.Serializable;
import java.util.Locale;

/**
 * @filename: CPUInfo
 * @author: Frewen.Wong
 * @time: 2021/8/14 14:42
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
public class CpuInfo implements Serializable {

    // 总的cpu使用率(user + system + IO操作 + 其他)
    public double totalUseRatio;
    // app的cpu使用率
    public double appCpuRatio;
    // 用户进程cpu使用率
    public double userCpuRatio;
    // 系统进程cpu使用率
    public double sysCpuRatio;
    // io等待时间占比
    public double ioWaitRatio;

    public CpuInfo(double totalUseRatio, double appCpuRatio, double userCpuRatio, double sysCpuRatio, double
            ioWaitRatio) {
        this.totalUseRatio = totalUseRatio;
        this.appCpuRatio = appCpuRatio;
        this.userCpuRatio = userCpuRatio;
        this.sysCpuRatio = sysCpuRatio;
        this.ioWaitRatio = ioWaitRatio;
    }

    public CpuInfo() {
    }

    @Override
    public String toString() {
        return "CpuInfo: app:" +
                String.format(Locale.US, "%.1f", appCpuRatio * 100f) +
                "% , total:" +
                String.format(Locale.US, "%.1f", totalUseRatio * 100f) +
                "% , user:" +
                String.format(Locale.US, "%.1f", userCpuRatio * 100f) +
                "% , system:" +
                String.format(Locale.US, "%.1f", sysCpuRatio * 100f) +
                "% , iowait:" +
                String.format(Locale.US, "%.1f", ioWaitRatio * 100f) + "%";
    }
}
