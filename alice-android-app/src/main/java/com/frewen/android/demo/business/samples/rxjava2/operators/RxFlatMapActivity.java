package com.frewen.android.demo.business.samples.rxjava2.operators;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.frewen.android.demo.R;
import com.frewen.demo.library.ui.activity.BaseToolBarActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxFlatMapActivity extends BaseToolBarActivity {
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
        /**
         * FlatMap 是一个很有趣的东西，我坚信你在实际开发中会经常用到。
         * 它可以把一个发射器Observable 通过某种方法转换为多个Observables，
         * 然后再把这些分散的Observables装进一个单一的发射器Observable。
         *
         * 但有个需要注意的是，flatMap并不能保证事件的顺序，
         * 如果需要保证，需要用到我们下面要讲的ConcatMap。
         */
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.i(TAG, "Observable emit 1 on " + Thread.currentThread().getName() + "--->" + "\n");
                emitter.onNext(1);
                Log.i(TAG, "Observable emit 2 on " + Thread.currentThread().getName() + "--->" + "\n");
                emitter.onNext(2);
                Log.i(TAG, "Observable emit 3 on " + Thread.currentThread().getName() + "--->" + "\n");
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                //// 这个方法是运行在子线程的
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer + " on " + Thread.currentThread().getName());
                }
                int delayTime = (int) (1 + Math.random() * 10);
                // 一个发射器Observable 通过某种方法转换为多个Observables，
                // 然后再把这些分散的Observables装进一个单一的发射器Observable。

                // 一切都如我们预期中的有意思，为了区分concatMap（下一个会讲），
                // 我在代码中特意动了一点小手脚，我采用一个随机数，生成一个时间，
                // 然后通过delay（后面会讲）操作符，做一个小延时操作，
                // 而查看Log日志也确认验证了我们上面的说法，它是无序的。
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    //// 这个方法是运行在主线程
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, "flatMap : accept : " + s + "  on  " + Thread.currentThread().getName() + "\n");
                        mRxOperatorsText.append("flatMap : accept : " + s + "\n");
                    }
                });
    }

}