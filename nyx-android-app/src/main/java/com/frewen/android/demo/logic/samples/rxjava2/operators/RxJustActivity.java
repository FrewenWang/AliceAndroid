package com.frewen.android.demo.logic.samples.rxjava2.operators;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.frewen.android.demo.R;
import com.frewen.demo.library.ui.activity.BaseToolBarActivity;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxJustActivity extends BaseToolBarActivity {
    private static final String TAG = "RxCreateActivity";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.rx_operators_text)
    protected TextView mRxOperatorsText;


    @Override
    protected void initView(Bundle savedInstanceState) {

    }


    @OnClick(R.id.rx_operators_btn)
    public void onViewClicked() {
        // just，没什么好说的，其实在前面各种例子都说明了，就是一个简单的发射器依次调用onNext()方法。
        // 这个操作符非常的简单、通俗、易懂，就是简单的去重嘛，我甚至都不想贴代码，但人嘛，总得持之以恒。
        /// 在经过distinct() 后接收器接收到的事件只有1,2,3,4,5了。
        // RxJava 2.x 新增Consumer，可自定义实现，accept 里面相当于原本的onNext
        /// Observable.just就是进行简单的发送事件，类似于之前的emitter.onNext(1)
        Observable.just(1, 1, 1, 2, 2, 3, 4, 5)
                .distinct()  // distinct是不同的，独特的，我们我们主要针对事件进行去重
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        mRxOperatorsText.append("distinct : " + integer + "n");
                        Log.e(TAG, "distinct : " + integer + "n");
                    }
                });

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
}