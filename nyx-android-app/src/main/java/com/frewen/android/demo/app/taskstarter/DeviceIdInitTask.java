package com.frewen.android.demo.app.taskstarter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.frewen.aura.framework.taskstarter.BaseModuleTask;

/**
 * @filename: DeviceIdInitTask
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/21 14:57
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class DeviceIdInitTask extends BaseModuleTask {

    private String mDeviceId;

    @SuppressLint("MissingPermission")
    @Override
    public void run() {
        TelephonyManager manager = (TelephonyManager) mContext.getSystemService(
                Context.TELEPHONY_SERVICE);
        mDeviceId = manager.getDeviceId();
    }

    @Override
    public boolean needWait() {
        return false;
    }
}
