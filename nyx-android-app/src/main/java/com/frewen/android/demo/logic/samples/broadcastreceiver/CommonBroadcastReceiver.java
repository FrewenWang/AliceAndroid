package com.frewen.android.demo.logic.samples.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class CommonBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "CommonBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");
    }
}