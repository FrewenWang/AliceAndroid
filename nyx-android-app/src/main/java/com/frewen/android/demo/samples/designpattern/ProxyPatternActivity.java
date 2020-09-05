package com.frewen.android.demo.samples.designpattern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.frewen.android.demo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProxyPatternActivity extends AppCompatActivity implements View.OnClickListener {

    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procy_pattern);
        ButterKnife.bind(this);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, NotificationActivity.class), PendingIntent.FLAG_UPDATE_CURRENT));


    }

    @Override
    public void onClick(View v) {

    }

    @OnClick(R.id.btn)
    public void clickBtn() {

    }
}