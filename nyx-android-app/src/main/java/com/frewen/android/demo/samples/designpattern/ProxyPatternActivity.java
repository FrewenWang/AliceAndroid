package com.frewen.android.demo.samples.designpattern;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.frewen.android.demo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class ProxyPatternActivity extends AppCompatActivity implements View.OnClickListener {

    private NotificationManager notificationManager;
    private Notification.Builder builder;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy_pattern);
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {

    }

    @OnClick(R.id.btn)
    public void clickBtn() {
        showNotification();
    }


    private void showNotification() {
        //// 我们先定义一个意图.这个意图
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(ProxyPatternActivity.this, 0, intent, FLAG_UPDATE_CURRENT);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= 26) {
            //当sdk版本大于26
            String id = "channel_1";
            String description = "143";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(id, description, importance);
//                     channel.enableLights(true);
//                     channel.enableVibration(true);//
            manager.createNotificationChannel(channel);
            Notification notification = new Notification.Builder(ProxyPatternActivity.this, id)
                    .setCategory(Notification.CATEGORY_MESSAGE)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("This is a content title")
                    .setContentText("This is a content text")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();
            manager.notify(1, notification);
        } else {
            //当sdk版本小于26
            Notification notification = new NotificationCompat.Builder(ProxyPatternActivity.this)
                    .setContentTitle("This is content title")
                    .setContentText("This is content text")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();
            manager.notify(1, notification);
        }
    }


    /**
     * 之前我们说过，通知渠道一旦创建，控制权就在用户手中，如果有一个重要通知渠道被用户手动关闭了，我们就要提醒用户去手动打开该渠道。
     * getNotificationChannel()方法可以获取指定的通知渠道对象，
     * getNotificationChannels() 可以获取所有通知对象的集合，保存在一个list中
     */
    private void checkoutNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = manager.getNotificationChannel("channel_1");
            if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
                startActivity(intent);
                Toast.makeText(this, "重要通知不能关闭，请手动将通知打开", Toast.LENGTH_SHORT).show();
            }
        }
    }
}