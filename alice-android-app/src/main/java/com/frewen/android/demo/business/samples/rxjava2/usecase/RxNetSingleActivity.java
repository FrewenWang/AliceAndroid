package com.frewen.android.demo.business.samples.rxjava2.usecase;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.frewen.android.demo.R;
import com.frewen.android.demo.business.model.ExpressInfo;
import com.frewen.demo.library.ui.activity.BaseToolBarActivity;
import com.google.gson.Gson;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 我们采用OKHttp配合map、doNext、线程切换等来进行简单的网络请求
 *
 * 1、通过 Observable.create() 方法，调用 OkHttp 网络请求;
 * 2、通过 map操作符结合Gson , 将 Response 转换为 bean 类;
 * 3、通过 doOnNext() 方法，解析 bean 中的数据，并进行数据库存储等操作;
 * 4、调度线程，在子线程进行耗时操作任务，在主线程更新 UI;
 * 5、通过 subscribe(),根据请求成功或者失败来更新 UI。
 *
 * 文章参考：https://github.com/nanchen2251/RxJava2Examples
 */
public class RxNetSingleActivity extends BaseToolBarActivity {
    private static final String TAG = "RxNetSingleActivity";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.rx_operators_text)
    protected TextView mRxOperatorsText;

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_rx_create;
    }

    @Override
    protected void initToolBar() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @SuppressLint("CheckResult")
    @OnClick(R.id.rx_operators_btn)
    public void onViewClicked() {
        Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
                Log.e(TAG, "网络请求开始执行:" + Thread.currentThread().getName() + "\n");
                Request.Builder builder = new Request.Builder()
                        .url("http://www.kuaidi100.com/query?type=yuantong&postid=11111111111")
                        .get();
                Request request = builder.build();
                Call call = new OkHttpClient().newCall(request);
                /// 进行同步的网络请求
                Response response = call.execute();
                Log.e(TAG, "网络请求开始执行:" + Thread.currentThread().getName() + "   Response : ");
                // 通过发射器来进行发射
                emitter.onNext(response);
            }
        }).map(new Function<Response, ExpressInfo>() {
            @Override
            public ExpressInfo apply(Response response) throws Exception {
                Log.e(TAG, "Map中间结果进行处理:" + Thread.currentThread().getName() + "\n");
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        Log.e(TAG, "map:转换前:" + body);
                        return new Gson().fromJson(body.string(), ExpressInfo.class);
                    }
                }
                return null;
            }
        }).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<ExpressInfo>() {
            @Override
            public void accept(@NonNull ExpressInfo s) throws Exception {
                Log.e(TAG, "doOnNext逻辑开始处理 线程:" + Thread.currentThread().getName() + "\n");
                mRxOperatorsText.append("\ndoOnNext 线程:" + Thread.currentThread().getName() + "\n");
                Log.e(TAG, "doOnNext: 保存成功：" + s.toString() + "\n");
                mRxOperatorsText.append("doOnNext: 保存成功：" + s.toString() + "\n");

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ExpressInfo>() {
            @Override
            public void accept(@NonNull ExpressInfo data) throws Exception {
                Log.e(TAG, "subscribe 线程:" + Thread.currentThread().getName() + "\n");
                mRxOperatorsText.append("\nsubscribe 线程:" + Thread.currentThread().getName() + "\n");
                Log.e(TAG, "成功:" + data.toString() + "\n");
                mRxOperatorsText.append("成功:" + data.toString() + "\n");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.e(TAG, "subscribe 线程:" + Thread.currentThread().getName() + "\n");
                mRxOperatorsText.append("\nsubscribe 线程:" + Thread.currentThread().getName() + "\n");

                Log.e(TAG, "失败：" + throwable.getMessage() + "\n");
                mRxOperatorsText.append("失败：" + throwable.getMessage() + "\n");
            }
        });

    }

}