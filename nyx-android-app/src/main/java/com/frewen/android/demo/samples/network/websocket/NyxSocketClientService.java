package com.frewen.android.demo.samples.network.websocket;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;

import androidx.core.app.NotificationCompat;

import android.util.Log;

import com.frewen.android.demo.MainBackActivity;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

import static android.app.Notification.VISIBILITY_PUBLIC;

/**
 * @filename: WebSocketClientService
 * @introduction: 一般来说即时通讯功能都希望像QQ微信这些App一样能在后台保持运行，当然App保活这个问题本身就是个伪命题，
 *         我们只能尽可能保活，所以首先就是建一个Service，将webSocket的逻辑放入服务中运行并尽可能保活，让websocket保持连接。
 * @author: Frewen.Wong
 * @time: 2019-06-25 08:45
 *         Copyright ©2019 Frewen.Wong. All Rights Reserved.
 */
public class NyxSocketClientService extends Service {
    private static final String TAG = "WebSocketClientService";
    /**
     * websocket测试地址
     */
    private static final String WEB_SERVICE_URL = "ws://echo.websocket.org";
    private NyxWebSocketClient client;
    private WebSocketClientBinder mBinder = new WebSocketClientBinder();

    /**
     * 构造函数
     */
    public NyxSocketClientService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "FMsg:onCreate() called");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "FMsg:onStartCommand() called with: intent = [" + intent + "], flags = ["
                + flags + "], startId = [" + startId + "]");
        // 初始化WebSocket
        initSocketClient();

        return super.onStartCommand(intent, flags, startId);
    }

    private void initSocketClient() {
        URI uri = URI.create(WEB_SERVICE_URL);
        client = new NyxWebSocketClient(uri) {
            @Override
            public void onMessage(String message) {
                Log.e(TAG, "收到的消息：" + message);
                Intent intent = new Intent();
                intent.setAction("com.frewen.service.callback.content");
                intent.putExtra("message", message);
                sendBroadcast(intent);

                checkLockAndShowNotification(message);
            }

            @Override
            public void onOpen(ServerHandshake handshakedata) {
                super.onOpen(handshakedata);
                Log.e(TAG, "websocket连接成功");
            }
        };
        connect();
    }

    //-----------------------------------消息通知--------------------------------------------------------
    private void checkLockAndShowNotification(String message) {
        //管理锁屏的一个服务
        KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        //锁屏
        if (km.isKeyguardLocked()) {
            //获取电源管理器对象
            PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
            if (!pm.isScreenOn()) {
                @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl
                        = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
                        | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
                wl.acquire();  //点亮屏幕
                wl.release();  //任务结束后释放
            }
            sendNotification(message);
        } else {
            sendNotification(message);
        }
    }

    /**
     * 发送通知
     *
     * @param content
     */
    private void sendNotification(String content) {
        Intent intent = new Intent();
        intent.setClass(this, MainBackActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                // 设置该通知优先级
                .setPriority(Notification.PRIORITY_MAX)
//                .setSmallIcon(R.drawable.icon)
                .setContentTitle("服务器")
                .setContentText(content)
                .setVisibility(VISIBILITY_PUBLIC)
                .setWhen(System.currentTimeMillis())
                // 向通知添加声音、闪灯和振动效果
                .setDefaults(Notification.DEFAULT_VIBRATE
                        | Notification.DEFAULT_ALL
                        | Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent)
                .build();
        //id要保证唯一
        notifyManager.notify(1, notification);
    }

    /**
     * 建立WebSocket连接
     */
    private void connect() {
        new Thread("web_socket-connect") {
            @Override
            public void run() {
                try {
                    //connectBlocking多出一个等待操作，会先连接再发送，否则未连接发送会报错
                    client.connectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    //用于Activity和service通讯
    class WebSocketClientBinder extends Binder {
        public NyxSocketClientService getService() {
            return NyxSocketClientService.this;
        }
    }
}
