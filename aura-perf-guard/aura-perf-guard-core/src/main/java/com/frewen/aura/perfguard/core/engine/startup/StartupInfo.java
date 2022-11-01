package com.frewen.aura.perfguard.core.engine.startup;


import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.Keep;
import androidx.annotation.StringDef;

/**
 * @filename: StartupInfo
 * @author: Frewen.Wong
 * @time: 2022/3/1 23:48
 * @version: 1.0.0
 * @introduction: 监控应用启动的时间信息的实体类
 * @copyright: Copyright ©2022 Frewen.Wong. All Rights Reserved.
 */
@Keep
public class StartupInfo implements Serializable {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            StartupType.COLD,
            StartupType.HOT
    })
    public @interface StartupType {
        String COLD = "cold";
        String HOT = "hot";
    }

    public @StartupType
    String startupType;
    public long startupTime;


    public StartupInfo(@StartupType String startupType, long startupTime) {
        this.startupType = startupType;
        this.startupTime = startupTime;
    }


    @Override
    public String toString() {
        return "StartupInfo{" +
                "startupType='" + startupType + '\'' +
                ", startupTime=" + startupTime +
                '}';
    }
}
