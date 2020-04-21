package com.frewen.android.demo.samples.network.websocket;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * @filename: MyWebSocketClient
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2019-06-25 08:36
 * Copyright ©2019 Frewen.Wong. All Rights Reserved.
 */
public class MyWebSocketClient extends WebSocketClient {
    private static final String TAG = "MyWebSocketClient";

    /**
     * 构造函数
     *
     * @param serverUri
     */
    public MyWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d(TAG, "FMsg:onOpen() called with: handshakedata = [" + handshakedata + "]");
    }

    @Override
    public void onMessage(String message) {
        Log.d(TAG, "FMsg:onMessage() called with: message = [" + message + "]");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d(TAG, "FMsg:onClose() called with: code = [" + code + "], reason = ["
                + reason + "], remote = [" + remote + "]");
    }

    @Override
    public void onError(Exception ex) {
        Log.d(TAG, "FMsg:onError() called with: ex = [" + ex + "]");
    }
}
