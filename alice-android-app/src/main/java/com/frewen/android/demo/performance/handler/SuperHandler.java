package com.frewen.android.demo.performance.handler;

import android.os.Handler;
import android.os.Message;

/**
 * @filename: SupserHandler
 * @author: Frewen.Wong
 * @time: 2020/11/9 22:56
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class SuperHandler extends Handler {

    private long mStartTime = System.currentTimeMillis();


    @Override
    public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
        boolean send = super.sendMessageAtTime(msg, uptimeMillis);
        return send;
    }

    @Override
    public void dispatchMessage(Message msg) {
        super.dispatchMessage(msg);
    }
}
