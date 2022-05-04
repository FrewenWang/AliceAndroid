package com.frewen.android.demo.logic.samples.rxjava2.operators;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.frewen.android.demo.R;
import com.frewen.demo.library.ui.activity.BaseToolBarActivity;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxIntervalActivity extends BaseToolBarActivity {
    private static final String TAG = "RxCreateActivity";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.rx_operators_text)
    protected TextView mRxOperatorsText;
    private Disposable disposable;

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


    @OnClick(R.id.rx_operators_btn)
    public void onViewClicked() {
        mRxOperatorsText.append("timer start11111 : " + System.currentTimeMillis() + "n");
        Log.e(TAG, "timer start : " + System.currentTimeMillis() + "n");

        /**
         * timer 很有意思，相当于一个定时任务。在1.x 中它还可以执行间隔逻辑，但在2.x中此功能被交给了 interval，
         * 下一个会介绍。但需要注意的是，timer 和 interval 均默认在新线程。
         *
         * 下面我们来看interval。这个我们启动了一个延迟3秒的，周期为3秒的任务
         */
        disposable = Observable.interval(3, 3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) //由于interval默认在新线程，所以需要切换回主线程
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mRxOperatorsText.append("timer3333 :" + aLong + " at " + System.currentTimeMillis() + "n");
                        Log.e(TAG, "timer :" + aLong + " at " + System.currentTimeMillis() + "n");
                    }
                });
        mRxOperatorsText.append("timer start 22222: " + System.currentTimeMillis() + "n");
        Log.e(TAG, "timer start : " + System.currentTimeMillis() + "n");

        /// 如同 Log 日志一样，第一次延迟了3秒后接收到，后面每次间隔了2秒。
        // 然而，心细的小伙伴可能会发现，由于我们这个是间隔执行，所以当我们的Activity都销毁的时候，
        // 实际上这个操作还依然在进行，所以，我们得花点小心思让我们在不需要它的时候干掉它。
        // 查看源码发现，我们subscribe(Cousumer<? super T> onNext)返回的是Disposable，我们可以在这上面做文章。
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}