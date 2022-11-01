package com.frewen.aura.perfguard.core.monitor;

import android.content.Context;

/**
 * @filename: IPerfGuardMonitor
 * @author: Frewen.Wong
 * @time: 2021/8/17 23:33
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
public interface IPerfGuardMonitor {

    void starMonitor(Context context, int port);

    void stopMonitor();
    
}
