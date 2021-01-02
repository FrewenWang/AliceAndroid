package com.frewen.android.demo.logic.samples.androidapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.frewen.android.demo.R;
import com.frewen.aura.framework.ui.BaseActivity;

public class ActivityDemoActivity extends BaseActivity {

    private static final int REQUEST_CODE = 10000;
    private TextView mTvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        mTvShow = findViewById(R.id.textView3);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到新打开Activity关闭后返回的数据
                //第二个参数为请求码，可以根据业务需求自己编号
                Intent intent = new Intent(ActivityDemoActivity.this, SecondActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }


    /**
     * 为了得到传回的数据，必须在前面的Activity中（指FirstActivity类）重写onActivityResult方法
     *
     * @param requestCode 请求码，即调用startActivityForResult()传递过去的值
     * @param resultCode  返回码，结果码用于标识返回数据来自哪个新Activity
     * @param data        更新后的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                mTvShow.setText(data.getExtras().getString("second") + "requestCode:" + requestCode + "resultCode:" + resultCode);
            }
        }
    }
}