package com.frewen.android.demo.logic.samples.network.aura;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.frewen.android.demo.R;
import com.frewen.android.demo.logic.model.Post;
import com.frewen.network.core.AuraRxHttp;
import com.frewen.network.listener.SimpleResponseCallback;
import com.frewen.network.response.Response;
import com.frewen.network.response.exception.AuraException;

public class AuraNetWorkDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aura_net_work_demo);


    }

    /**
     * 测试Get请求
     *
     * @param view
     */
    public void onGet(View view) {
        AuraRxHttp.get("http://www.baidu.com")
                .readTimeOut(30 * 1000)             //局部定义读超时 ,可以不用定义
                .writeTimeOut(30 * 1000)
                // .addHeader("","")                   //设置头参数
                // .addParam("name", "张三")             //设置参数
                //.addInterceptor()
                //.addConverterFactory()
                //.addCookie()
                .execute(new SimpleResponseCallback<Post>() {
                    @Override
                    public void onSuccess(Response<Post> response) {

                    }

                    @Override
                    public void onError(AuraException exception) {

                    }
                });
    }


    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}