package com.frewen.aura.perfgurad.monitor.server;

import android.util.Log;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.http.WebSocket;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @filename: PerfGuardMonitor
 * @author: Frewen.Wong
 * @time: 2021/8/18 08:38
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
public class PerfGuardMonitorServer implements IMessager {
    private static final String TAG = "PerfGuardMonitorServer";
    private final int mPort;
    private final Object mLockForWebSockets = new Object();
    private final ArrayList<WebSocket> mWebSockets;
    private AsyncHttpServer mServer;
    private OnServerMonitorListener serverMonitorListener;

    public PerfGuardMonitorServer(int port) {
        mPort = port;
        mWebSockets = new ArrayList<>();
        mServer = new AsyncHttpServer();
        mServer.websocket("/refresh", (webSocket, request) -> {
            synchronized (mLockForWebSockets) {
                mWebSockets.add(webSocket);
                Log.d(TAG, "connection build. current count:" + mWebSockets.size());
            }
            webSocket.setClosedCallback(ex -> {
                synchronized (mLockForWebSockets) {
                    mWebSockets.remove(webSocket);
                    Log.d(TAG, "connection closed. current count:" + mWebSockets.size());
                }
            });

        });
    }

    public void start() {
        mServer.listen(mPort);
    }

    public void stop() {
        mServer.stop();
    }


    @Override
    public void sendMessage(String message) {
        for (WebSocket webSocket : mWebSockets) {
            if (webSocket.isOpen()) {
                webSocket.send(message);
            }
        }
    }

    public void setOnServerMonitorListener(OnServerMonitorListener listener) {
        this.serverMonitorListener = listener;
    }

    /**
     * server的消息回调
     */
    public interface OnServerMonitorListener {
        void onClientAdded(List<WebSocket> webSockets, WebSocket added);

        void onClientRemoved(List<WebSocket> webSockets, WebSocket removed);

        void onHttpRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response);

        void onWebSocketRequest(WebSocket webSocket, String messageFromClient);
    }
}
