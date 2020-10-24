package com.frewen.android.demo.logic.samples.androidapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.frewen.android.demo.R;

public class SecondActivity extends AppCompatActivity {

    private Button mBtnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seconed);

        mBtnClose = findViewById(R.id.button33);

        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //把需要返回的数据存放在intent
                intent.putExtra("second", "我是第二页的信息！");
                //设置给其调用者设置返回数据,
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}