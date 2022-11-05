package com.frewen.aura.perfgurad.monitor;

import android.content.Context;

import com.frewen.aura.perfguard.core.monitor.IPerfGuardMonitor;
import com.frewen.aura.perfgurad.monitor.server.EngineDataDriver;
import com.frewen.aura.perfgurad.monitor.server.PerfGuardMonitorServer;
import com.frewen.aura.toolkits.utils.Preconditions;
import com.koushikdutta.async.http.WebSocket;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;

import java.util.List;

/**
 * @filename: PerfGuardMonitor
 * @author: Frewen.Wong
 * @time: 2021/8/18 08:32
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
public class PerfGuardMonitor implements IPerfGuardMonitor {

    private boolean mIsWorking = false;
    private PerfGuardMonitorServer mPerfGuardMonitorServer;

    @Override
    public synchronized void starMonitor(Context context, int port) {
        if (mIsWorking) {
            return;
        }
        mIsWorking = true;
        Preconditions.notNull(context);
        mPerfGuardMonitorServer = new PerfGuardMonitorServer(port);
        mPerfGuardMonitorServer.setOnServerMonitorListener(new PerfGuardMonitorServer.OnServerMonitorListener() {
            @Override
            public void onClientAdded(List<WebSocket> webSockets, WebSocket added) {
                EngineDataDriver.instance().start(mPerfGuardMonitorServer);
            }

            @Override
            public void onClientRemoved(List<WebSocket> webSockets, WebSocket removed) {

            }

            @Override
            public void onHttpRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {

            }

            @Override
            public void onWebSocketRequest(WebSocket webSocket, String messageFromClient) {

            }
        });
    }

    @Override
    public void stopMonitor() {
        if (mPerfGuardMonitorServer != null) {
            mPerfGuardMonitorServer.stop();
            mPerfGuardMonitorServer = null;
        }
        mIsWorking = false;
    }

}
