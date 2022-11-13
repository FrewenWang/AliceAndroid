package com.frewen.android.demo.business.samples;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.frewen.android.demo.R;
import com.frewen.android.demo.business.samples.concurrent.HandlerThreadActivity;
import com.frewen.android.demo.business.model.ContentData;
import com.frewen.android.demo.business.samples.bitmap.BitmapDemoActivity;
import com.frewen.android.demo.business.samples.bluetooth.BlueToothDemoActivity;
import com.frewen.android.demo.business.samples.eventbus.EventBusActivity;
import com.frewen.android.demo.business.samples.fragment.FragmentDemoActivity;
import com.frewen.android.demo.business.samples.hook.HookDemoActivity;
import com.frewen.android.demo.business.samples.ipc.IPCDemoActivity;
import com.frewen.android.demo.business.samples.ipc.client.ContentProviderActivity;
import com.frewen.android.demo.business.samples.jobscheduler.JobSchedulerDemoActivity;
import com.frewen.android.demo.business.samples.network.FreeNetWorkDemoActivity;
import com.frewen.android.demo.business.samples.okhttp.OkHttp3Activity;
import com.frewen.android.demo.business.samples.view.ViewDemoActivity;
import com.frewen.android.demo.business.samples.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity
 */
public class SampleDemoActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView contentList;
    private List<ContentData> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "FMsg:onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        setContentView(R.layout.activity_main);

        initData();

        contentList = findViewById(R.id.item_list);
        contentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(SampleDemoActivity.this, mDatas.get(position).getClazz()));
            }
        });
    }

    private void initData() {
        mDatas = new ArrayList<>();
        mDatas.add(new ContentData("HandlerThread学习", HandlerThreadActivity.class));
        mDatas.add(new ContentData("ContentProvider学习", ContentProviderActivity.class));
        mDatas.add(new ContentData("RxJava2的学习", ContentProviderActivity.class));
        mDatas.add(new ContentData("OkHttp3学习", OkHttp3Activity.class));
        mDatas.add(new ContentData("EventBus学习", EventBusActivity.class));
        mDatas.add(new ContentData("Fragment使用学习", FragmentDemoActivity.class));
        mDatas.add(new ContentData("AgoraDemo学习", AgoraDemoActivity.class));
        mDatas.add(new ContentData("FreeNetWork学习", FreeNetWorkDemoActivity.class));
        mDatas.add(new ContentData("View的基础知识学习", ViewDemoActivity.class));
        mDatas.add(new ContentData("WebView的基础学习", WebViewActivity.class));
        mDatas.add(new ContentData("BlueTooth基础学习", BlueToothDemoActivity.class));
        mDatas.add(new ContentData("JobScheduler学习", JobSchedulerDemoActivity.class));
        mDatas.add(new ContentData("IPC跨进程通信学习", IPCDemoActivity.class));
        mDatas.add(new ContentData("Hook技术学习", HookDemoActivity.class));
        mDatas.add(new ContentData("Bitmap的基础知识学习", BitmapDemoActivity.class));
    }
}
