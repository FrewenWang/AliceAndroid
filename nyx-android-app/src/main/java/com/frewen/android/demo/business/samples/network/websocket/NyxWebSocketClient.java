package com.frewen.android.demo.business.samples.network.websocket;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * @filename: MyWebSocketClient
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2019-06-25 08:36
 *         Copyright ©2019 Frewen.Wong. All Rights Reserved.
 */
public class NyxWebSocketClient extends WebSocketClient {
    private static final String TAG = "NyxWebSocketClient";

    /**
     * 构造函数
     * 构造方法中的new Draft_6455()代表使用的协议版本，这里可以不写或者写成这样即可。
     *
     * @param serverUri
     */
    public NyxWebSocketClient(URI serverUri) {
        super(serverUri, new Draft_6455());
    }

    /**
     * 其中onOpen()方法在webSocket连接开启时调用
     *
     * @param handshakeData
     */
    @Override
    public void onOpen(ServerHandshake handshakeData) {
        Log.d(TAG, "FMsg:onOpen() called with: HttpStatus:"
                + handshakeData.getHttpStatus() + ",HttpStatusMessage:" + handshakeData.getHttpStatusMessage());
    }

    /**
     * onMessage()方法在接收到消息时调用
     *
     * @param message
     */
    @Override
    public void onMessage(String message) {
        Log.d(TAG, "FMsg:onMessage() called with: message = [" + message + "]");
    }

    /**
     * onClose()方法在连接断开时调用
     *
     * @param code
     * @param reason
     * @param remote
     */
    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d(TAG, "FMsg:onClose() called with: code = [" + code + "], reason = ["
                + reason + "], remote = [" + remote + "]");
    }

    /**
     * onError()方法在连接出错时调用
     *
     * @param ex
     */
    @Override
    public void onError(Exception ex) {
        Log.d(TAG, "FMsg:onError() called with: ex = [" + ex + "]");
    }
}
