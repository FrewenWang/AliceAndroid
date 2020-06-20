package com.frewen.android.demo.samples.network;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.frewen.android.demo.R;
import com.frewen.android.demo.samples.network.websocket.NyxWebSocketClient;

import java.net.URI;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @filename: WebSocketDemoActivity
 * @introduction: WebSocket的Demo
 *
 *         文章参考：https://www.cnblogs.com/xch-yang/p/11726497.html
 *         https://github.com/TooTallNate/Java-WebSocket
 *         上面这个开源开源框架也有很多例子：https://github.com/TooTallNate/Java-WebSocket/tree/master/src/main/example
 * @author: Frewen.Wong
 * @time: 2019-06-25 08:35
 * @copyright: Copyright ©2019 Frewen.Wong. All Rights Reserved.
 */
public class WebSocketDemoActivity extends AppCompatActivity {

    private NyxWebSocketClient client;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //服务与活动成功绑定
            Log.e("MainActivity", "服务与活动成功绑定");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //服务与活动断开
            Log.e("MainActivity", "服务与活动成功断开");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_socket_demo);
    }


    public void startWebService(View view) {
        /**
         * 为了方便对接收到的消息进行处理，可以在这重写onMessage()方法。
         * 初始化客户端时需要传入websocket地址（测试地址：ws://echo.websocket.org），
         * websocket协议地址大致是这样的:
         * ws:// ip地址 : 端口号
         */
        URI uri = URI.create("ws://echo.websocket.org");
        client = new NyxWebSocketClient(uri) {
            @Override
            public void onMessage(String message) {
                //message就是接收到的消息
                Log.e("NyxWebSocketClient", message);
            }
        };

        // 连接时可以使用connect()方法或connectBlocking()方法，建议使用connectBlocking()方法，
        // connectBlocking多出一个等待操作，会先连接再发送。
        try {
            client.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void sendMsg(View view) {
        if (client != null && client.isOpen()) {
            client.send("你好");
        }
    }

    public void closeWebService(View view) {
        try {
            if (null != client) {
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client = null;
        }
    }
}
