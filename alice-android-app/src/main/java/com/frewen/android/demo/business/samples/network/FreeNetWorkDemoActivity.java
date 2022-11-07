package com.frewen.android.demo.business.samples.network;

import android.os.Bundle;
import android.view.View;

//import com.chaquo.python.Python;
//import com.chaquo.python.android.AndroidPlatform;
import com.frewen.android.demo.R;
import com.frewen.network.core.AuraRxHttp;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @filename: FreeNetWorkDemoActivity
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/7/9 10:41
 *         Copyright ©2019 Frewen.Wong. All Rights Reserved.
 */
public class FreeNetWorkDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_net_work_demo);
        initPython();
    }

    private void initPython() {
        //if (!Python.isStarted()) {
        //    Python.start(new AndroidPlatform(this));
        //}
    }

    /**
     * 测试登录
     * @param view
     */
    public void onLogin(View view) {
        //Python py = Python.getInstance();
//        py.getBuiltins()
//        py.getModule("httpstat").callAttr("main", "www.baidu.com");
        //py.getModule("hello").callAttr("print_numpy");
    }

    /**
     * 获取普通的Get请求
     * @param view
     */
    public void onGet(View view) {
        AuraRxHttp.getInstance();
    }
}
