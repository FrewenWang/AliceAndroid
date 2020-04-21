package com.frewen.android.demo.samples.network;

import android.os.Bundle;
import android.view.View;

import com.frewen.android.demo.R;
import com.frewen.network.core.FreeRxHttp;

import androidx.appcompat.app.AppCompatActivity;


public class FreeNetWorkDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_net_work_demo);
    }

    /**
     * 测试登录
     *
     * @param view
     */
    public void onLogin(View view) {

    }

    /**
     * 获取普通的Get请求
     *
     * @param view
     */
    public void onGet(View view) {
        FreeRxHttp.getInstance();
    }
}
