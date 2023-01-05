package com.frewen.android.demo.app.taskstarter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.frewen.aura.framework.taskstarter.AbsLaunchTask;

/**
 * @filename: DeviceIdInitTask
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/21 14:57
 * @Copyright Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class DeviceIdInitTask extends AbsLaunchTask {

    private String mDeviceId;

    @SuppressLint({"MissingPermission", "HardwareIds"})
    @Override
    public void execute() {
        TelephonyManager manager = (TelephonyManager) mContext.getSystemService(
                Context.TELEPHONY_SERVICE);
        mDeviceId = manager.getDeviceId();
    }
}