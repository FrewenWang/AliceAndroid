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
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RxCreateActivity extends BaseToolBarActivity {
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
        // 被监听者来创建
        /**
         * create操作符应该是最常见的操作符了，主要用于产生一个Obserable被观察者对象，
         * 为了方便大家的认知，以后的教程中统一把被观察者Observable称为发射器（上游事件），
         * 观察者Observer称为接收器（下游事件）。
         */
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                /// 这边是上游发射的实现，我们会通过键监听者来在下游进行监听
                mRxOperatorsText.append("Observable emit 1 --->" + "\n");
                Log.e(TAG, "Observable emit 1" + "\n");
                emitter.onNext(1);

                mRxOperatorsText.append("Observable emit 2 --->" + "\n");
                Log.e(TAG, "Observable emit 2" + "\n");
                emitter.onNext(2);

                mRxOperatorsText.append("Observable emit 3 --->" + "\n");
                Log.e(TAG, "Observable emit 3" + "\n");
                emitter.onNext(3);
                emitter.onComplete();

                mRxOperatorsText.append("Observable emit 4 --->" + "\n");
                Log.e(TAG, "Observable emit 4" + "\n");
                emitter.onNext(4);
            }
        }).subscribe(new Observer<Integer>() {
            private int index;
            /**
             * 并且2.x 中有一个Disposable概念，这个东西可以直接调用切断，
             * 可以看到，当它的isDisposed()返回为false的时候，接收器能正常接收事件，
             * 但当其为true的时候，接收器停止了接收。所以可以通过此参数动态控制接收事件了。
             */
            private Disposable mDisposable;

            /// 添加观察者
            @Override
            public void onSubscribe(Disposable d) {
                mRxOperatorsText.append("--->onSubscribe : " + d.isDisposed() + "\n");
                Log.e(TAG, "onSubscribe : " + d.isDisposed() + "\n");
                mDisposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                mRxOperatorsText.append("--->onNext : value : " + integer + "\n");
                Log.e(TAG, "onNext : value : " + integer + "\n");

                index++;
//                if (index == 2) {
//                    // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
//                    // 调用这个方法以后，上游哪怕继续发事件，下游也不再接收了！！！
//                    mDisposable.dispose();
//                    mRxOperatorsText.append("--->onNext : isDisposable : " + mDisposable.isDisposed() + "\n");
//                    Log.e(TAG, "onNext : isDisposable : " + mDisposable.isDisposed() + "\n");
//                }
            }

            /**
             * 另外一个值得注意的点是，在RxJava 2.x中，可以看到发射事件方法相比1.x多了一个throws Excetion，
             * 意味着我们做一些特定操作再也不用try-catch了。
             * @param e
             */
            @Override
            public void onError(Throwable e) {
                mRxOperatorsText.append("--->onError : value : " + e.getMessage() + "\n");
                Log.e(TAG, "onError : value : " + e.getMessage() + "\n");
            }

            @Override
            public void onComplete() {

                mRxOperatorsText.append("--->onComplete" + "\n");
                Log.e(TAG, "onComplete" + "\n");
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