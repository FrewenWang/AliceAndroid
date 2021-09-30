package com.frewen.aura.perfguard.core.engine.cpu;

import android.text.TextUtils;
import android.util.Log;

import com.frewen.aura.toolkits.common.IOStreamUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.VisibleForTesting;

/**
 * @filename: CPUUsageHelper
 * @author: Frewen.Wong
 * @time: 2021/8/14 14:58
 * @version: 1.0.0
 * @introduction: 用于获取CPU使用率的帮助类
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
public class CpuUsageHelper {
    private static final String TAG = "CpuUsageHelper";
    private static final int BUFFER_SIZE = 1024;
    private static CpuSnapshot sLastCpuSnapshot;

    /**
     * 判断是够有权限通过读取文件来获取CPU信息
     *
     * @param pId
     */
    private static boolean cpuFileUseAbility(int pId) {
        File stat = new File("/proc/stat");
        if (!stat.exists() || !stat.canRead()) {
            return false;
        }
        File statPid = new File("/proc/" + pId + "/stat");
        if (!statPid.exists() || !statPid.canRead()) {
            return false;
        }
        return true;
    }

    public static CpuInfo getCpuInfo(int pId) {
        boolean canReadFromFile = cpuFileUseAbility(pId);
        if (canReadFromFile) {
            if (sLastCpuSnapshot == null) {
                sLastCpuSnapshot = parse(getCpuRateOfDevice(), getCpuRateOfApp(pId));
                return new CpuInfo();
            } else {
                // 解析当前的CPU快照
                CpuSnapshot current = parse(getCpuRateOfDevice(), getCpuRateOfApp(pId));
                float totalTime = (current.total - sLastCpuSnapshot.total) * 1.0f;
                if (totalTime <= 0) {
                    Log.e(TAG, "totalTime must greater than 0");
                    return new CpuInfo();
                }
                long idleTime = current.idle - sLastCpuSnapshot.idle;
                double totalRatio = (totalTime - idleTime) / totalTime;
                double appRatio = (current.app - sLastCpuSnapshot.app) / totalTime;
                double userRatio = (current.user - sLastCpuSnapshot.user) / totalTime;
                double systemRatio = (current.system - sLastCpuSnapshot.system) / totalTime;
                double ioWaitRatio = (current.ioWait - sLastCpuSnapshot.ioWait) / totalTime;
                return new CpuInfo(filterCpuRatio(totalRatio), filterCpuRatio(appRatio),
                        filterCpuRatio(userRatio), filterCpuRatio(systemRatio),
                        filterCpuRatio(ioWaitRatio));
            }
        } else {
            return getCpuInfoFromShell(pId);
        }
    }

    /**
     * #CPU指标：user，nice, system, idle, iowait, irq, softirq
     * cpu  489749 197546 821466 47677396 38703 181223 80820 0 0 0
     * cpu0 173008 67906 243493 5583872 12333 58476 19142 0 0 0
     * cpu1 162066 55779 222512 5659148 10729 32349 19225 0 0 0
     * cpu2 63580 24512 164478 5849077 6191 28180 16010 0 0 0
     * cpu3 54810 20977 158560 5821697 5378 56664 23498 0 0 0
     * cpu4 8463 7861 8091 6192044 753 1030 301 0 0 0
     * cpu5 5533 6781 5354 6199366 576 757 334 0 0 0
     * cpu6 13209 8051 10533 6181016 1448 2183 942 0 0 0
     * cpu7 9077 5676 8441 6191172 1293 1580 1365 0 0 0
     */
    private static String getCpuRateOfDevice() {
        BufferedReader cpuReader = null;
        try {
            // BufferedReader来进行传入文件输入流读取CPU信息
            cpuReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/stat")), BUFFER_SIZE);
            String cpuRate = cpuReader.readLine();
            if (cpuRate == null) {
                return "";
            }
            return cpuRate.trim();
        } catch (Throwable e) {
            return "";
        } finally {
            IOStreamUtils.close(cpuReader);
        }
    }

    /**
     * # 进程ID 进程名                       进程状态 父进程ID 父进程组ID
     * 30629 (com.frewen.android.demo.debug) S 1004 1004 0 0 -1 1077952832 402812 1157592 3636 78
     * 16811 6742 1919 3879 20 0 41 0 8691031 14519648256 26741 18446744073709551615 1 1 0 0 0 0
     * 4612 1 1073775868 0 0 0 17 1 0 0 0 0 0 0 0 0 0 0 0 0 0
     *
     * @param pId
     */
    private static String getCpuRateOfApp(int pId) {
        BufferedReader pidReader = null;
        try {
            pidReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/" + pId + "/stat")), BUFFER_SIZE);
            String pidCpuRate = pidReader.readLine();
            if (pidCpuRate == null) {
                return "";
            }
            return pidCpuRate;
        } catch (Throwable throwable) {
            return "";
        } finally {
            IOStreamUtils.close(pidReader);
        }
    }


    @VisibleForTesting
    static CpuSnapshot parse(String allCpuRate, String pidCpuRate) {

        //#CPU指标：user，nice, system, idle, iowait, irq, softirq
        // cpu  489749 197546 821466 47677396 38703 181223 80820 0 0 0
        String[] cpuInfoArray = allCpuRate.split("\\s+");
        if (cpuInfoArray.length < 9) {
            throw new IllegalStateException("cpu info array size must great than 9");
        }
        long user = Long.parseLong(cpuInfoArray[2]);
        long nice = Long.parseLong(cpuInfoArray[3]);
        long system = Long.parseLong(cpuInfoArray[4]);
        long idle = Long.parseLong(cpuInfoArray[5]);
        long ioWait = Long.parseLong(cpuInfoArray[6]);
        long total = user + nice + system + idle + ioWait
                + Long.parseLong(cpuInfoArray[7])
                + Long.parseLong(cpuInfoArray[8]);
        // 机型应用占用的CPU相关的指标
        String[] pidCpuInfoList = pidCpuRate.split("\\s+");
        if (pidCpuInfoList.length < 17) {
            throw new IllegalStateException("pid cpu info array size must great than 17");
        }
        long appCpuTime = Long.parseLong(pidCpuInfoList[13])
                + Long.parseLong(pidCpuInfoList[14])
                + Long.parseLong(pidCpuInfoList[15])
                + Long.parseLong(pidCpuInfoList[16]);
        return new CpuSnapshot(user, system, idle, ioWait, total, appCpuTime);
    }


    /**
     * Tasks: 685 total,   1 running, 453 sleeping,   0 stopped,   0 zombie
     * Mem:   7656764k total,  7049688k used,   607076k free,   155716k buffers
     * Swap:  2621436k total,   515840k used,  2105596k free,  3681948k cached
     * 800%cpu   7%user   3%nice  28%sys 755%idle   0%iow   3%irq   3%sirq   0%host
     * PID      USER         PR  NI  VIRT   RES    SHR   S[%CPU] %MEM  TIME+  ARGS
     * 21637    shell        20   0  13M   4.6M   3.7M   R       10.3   0.0    0:00.07   top -n 1
     * 20750    root         20   0    0    0      0     I        6.8   0.0    0:02.43  [kworker/u16:5]
     * 816      system       -2  -8  2.1G  27M     22M   S        6.8   0.3    38:04.22  surfaceflinger
     * 14471    shell        20   0  60M   5.6M    4.9M  S        3.4   0.0    0:13.73  transport -conf+
     * 12900    u0_a41       20   0  4.1G   131M   131M  S        3.4   1.7    26:43.83  com.google.andr+
     *
     * @param pId
     */
    private static CpuInfo getCpuInfoFromShell(int pId) {
        CpuInfo cpuInfo = new CpuInfo();
        try {
            // 执行刷新一次的Top指令
            Process process = Runtime.getRuntime().exec("top -n 1 -m 10");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            int cpuIndex = -1;
            Map<String, Float> cpuDevice = null;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // 过滤读取到的空行
                if (TextUtils.isEmpty(line)) {
                    continue;
                }
                //解析系统资源占用行： 800%cpu   7%user   3%nice  28%sys 755%idle   0%iow   3%irq   3%sirq   0%host
                Map<String, Float> tempCpuDevice = parseCpuRateOfDeviceAndTotalByShell(line);
                if (tempCpuDevice != null) {
                    cpuDevice = tempCpuDevice;
                    Float total = cpuDevice.get("cpu");
                    Float user = cpuDevice.get("user");
                    Float sys = cpuDevice.get("sys");
                    Float idle = cpuDevice.get("idle");
                    Float iow = cpuDevice.get("iow");
                    if (total != null && total > 0 && iow != null && iow >= 0) {
                        cpuInfo.ioWaitRatio = iow / total;
                    }
                    if (total != null && total > 0 && sys != null && sys >= 0) {
                        cpuInfo.sysCpuRatio = sys / total;
                    }
                    if (total != null && total > 0 && idle != null && idle >= 0) {
                        cpuInfo.totalUseRatio = (total - idle) / total;
                    }
                    if (total != null && total > 0 && user != null && user >= 0) {
                        cpuInfo.userCpuRatio = user / total;
                    }
                    continue;
                }
                // 解析资源占用标题行
                // PID  USER  PR  NI  VIRT  RES SHR  S[%CPU] %MEM  TIME+  ARGS
                int tempIndex = parseCPUIndex(line);
                if (tempIndex != -1) {
                    cpuIndex = tempIndex;
                    continue;
                }
                float cpuTotal = cpuDevice == null ? 0 : cpuDevice.get("cpu");
                float tempAppRatio = parseCpuRateOfAppByShell(pId, line, cpuIndex, cpuTotal);
                if (tempAppRatio != -1) {
                    // 816 system   -2  -8 2.1G  27M  22M S  6.8   0.3  38:04.22 surfaceflinger
                    cpuInfo.appCpuRatio = tempAppRatio;
                    return cpuInfo;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cpuInfo;
    }

    /**
     * 找到资源占用标题行的CPU所在的列的索引值
     *
     * @param line
     */
    @VisibleForTesting
    private static int parseCPUIndex(String line) {
        if (line.contains("[%CPU]")) {
            // 按照空行进行内容的分隔
            String[] titles = line.split("\\s+");
            for (int i = 0; i < titles.length; i++) {
                if (titles[i].contains("CPU")) {
                    return i;
                }
            }
        }
        return -1;
    }


    /**
     * 通过shell脚本来解析CPU的使用率
     *
     * @param line
     */
    @VisibleForTesting
    private static Map<String, Float> parseCpuRateOfDeviceAndTotalByShell(String line) {
        // 将读取的shell脚本的每一行来做正则匹配
        // ^匹配开始，^\b 代表匹配以数字开始
        // ^\b+表示匹配一个或者多个数组
        // % 是指的后面加上%
        if (line.matches("^\\d+%\\w+.+\\d+%\\w+")) {
            String lineLowerCase = line.toLowerCase(Locale.US);
            // 对当前的CPU信息进行正则分隔.按照空白字符进行分隔
            String[] cpuList = lineLowerCase.split("\\s+");

            Map<String, Float> cpuMap = new HashMap<>();
            // 800%cpu   7%user   3%nice  28%sys 755%idle   0%iow   3%irq   3%sirq   0%host
            for (String s : cpuList) {
                // 针对每个Item按照%进行分隔
                String[] cpuItems = s.split("%");
                // 如果cpuItems的长度不等于2。说明处理异常
                if (cpuItems.length != 2) {
                    throw new IllegalStateException("parseCpuRateOfDeviceAndTotalByShell but cpuItem.length != 2");
                }
                try {
                    // Key是cpu、user nice 、sys、idle、iow、irq、sirq、host
                    cpuMap.put(cpuItems[1], Float.parseFloat(cpuItems[0]));
                } catch (Throwable e) {
                    throw new IllegalStateException("parseCpuRateOfDeviceAndTotalByShell but " + e);
                }
            }
            return cpuMap;
        }
        return null;
    }

    /**
     * 获取指定pId的进程的CPU占用量
     *
     * @param pId      当前进程的pId
     * @param line     top指令的行数据
     * @param cpuIndex CPU所在的列的索引值
     * @param cpuTotal 总的CPU数量
     */
    @VisibleForTesting
    private static float parseCpuRateOfAppByShell(int pId, String line, int cpuIndex, float cpuTotal) {
        // 根据第一列打印的pId。找到指定的进程的Top所在的行数据
        if (line.startsWith(String.valueOf(pId))) {
            // 异常处理
            if (cpuIndex == -1) {
                throw new IllegalStateException("parseCpuRateOfAppByShell but cpuIndex == -1:" + line);
            }

            String[] param = line.split("\\s+");
            if (param.length <= cpuIndex) {
                throw new IllegalStateException("parseCpuRateOfAppByShell but param.length <= cpuIndex:" + line);
            }
            String cpu = param[cpuIndex];
            if (cpu.endsWith("%")) {
                cpu = cpu.substring(0, cpu.lastIndexOf("%"));
            }
            if (cpuTotal <= 0) {
                throw new IllegalStateException("parseCpuRateOfAppByShell but cpuTotal == null || cpuTotal <= 0:" + line);
            }
            try {
                // 输出计算的当前进程的占用的CPU的百分比
                return Float.parseFloat(cpu) / cpuTotal;
            } catch (Throwable e) {
                throw new IllegalStateException("parseCpuRateOfAppByShell but " + e + ":" + line);
            }
        }
        return -1;
    }

    private static double filterCpuRatio(double ratio) {
        if (ratio < 0 || ratio > 1) {
            return 0;
        }
        return ratio;
    }

}
