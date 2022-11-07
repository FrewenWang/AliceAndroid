package com.frewen.android.demo.business.samples.rxjava2.operators;

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
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxTimerActivity extends BaseToolBarActivity {
    private static final String TAG = "RxCreateActivity";
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


    @OnClick(R.id.rx_operators_btn)
    public void onViewClicked() {
        mRxOperatorsText.append("timer start11111 : " + System.currentTimeMillis() + "n");
        Log.e(TAG, "timer start : " + System.currentTimeMillis() + "n");

        /**
         * timer 很有意思，相当于一个定时任务。在1.x 中它还可以执行间隔逻辑，但在2.x中此功能被交给了 interval，
         * 下一个会介绍。但需要注意的是，timer 和 interval 均默认在新线程。
         */
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) //timer默认在新线程，所以需要切换回主线程
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mRxOperatorsText.append("timer3333 :" + aLong + " at " + System.currentTimeMillis() + "n");
                        Log.e(TAG, "timer :" + aLong + " at " + System.currentTimeMillis() + "n");
                    }
                });
        mRxOperatorsText.append("timer start 22222: " + System.currentTimeMillis() + "n");
        Log.e(TAG, "timer start : " + System.currentTimeMillis() + "n");
    }

}