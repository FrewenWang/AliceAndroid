package com.frewen.android.demo.logic.samples.network.aura;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.observers.DisposableLambdaObserver;
import io.reactivex.observers.DisposableObserver;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.frewen.android.demo.R;
import com.frewen.android.demo.logic.model.Post;
import com.frewen.network.core.AuraRxHttp;
import com.frewen.network.listener.SimpleResponseCallback;
import com.frewen.network.response.Response;
import com.frewen.network.response.exception.AuraException;

public class AuraNetWorkDemoActivity extends AppCompatActivity {
    private static final String TAG = "AuraNetWorkDemoActivity";

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
        Observable<Post> observable = AuraRxHttp.get("http://www.baidu.com")
                .readTimeOut(30 * 1000)             //局部定义读超时 ,可以不用定义
                .writeTimeOut(30 * 1000)
                // .addHeader("","")                   //设置头参数
                // .addParam("name", "张三")             //设置参数
                //.addInterceptor()
                //.addConverterFactory()
                //.addCookie()
                .execute(Post.class);
        observable.subscribe(new DisposableObserver<Post>() {

            @Override
            public void onNext(@NonNull Post post) {
                Log.d(TAG, "FMsg:onNext() called with: post = [" + post + "]");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "FMsg:onError() called with: e = [" + e + "]");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "FMsg:onComplete() called");
            }
        });
    }


    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}