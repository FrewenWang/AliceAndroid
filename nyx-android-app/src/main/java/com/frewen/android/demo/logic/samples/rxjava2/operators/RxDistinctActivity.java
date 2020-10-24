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
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class RxDistinctActivity extends BaseToolBarActivity {
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
        // 这个操作符非常的简单、通俗、易懂，就是简单的去重嘛，我甚至都不想贴代码，但人嘛，总得持之以恒。
        /// 在经过distinct() 后接收器接收到的事件只有1,2,3,4,5了。
        Observable.just(1, 1, 1, 2, 2, 3, 4, 5)
                .distinct()  // distinct是不同的，独特的，我们我们主要针对事件进行去重
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
        mToolbar.setNavigationIcon(R.drawable.icon_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}