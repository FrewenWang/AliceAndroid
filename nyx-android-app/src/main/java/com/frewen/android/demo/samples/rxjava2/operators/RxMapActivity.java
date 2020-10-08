package com.frewen.android.demo.samples.rxjava2.operators;

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
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class RxMapActivity extends BaseToolBarActivity {
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
        mToolbar.setNavigationIcon(R.drawable.icon_back);
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
         * zip专用于合并事件，该合并不是连接（连接操作符后面会说），而是两两配对，
         * 也就意味着，最终配对出的Observable发射事件数目只和少的那个相同。
         *
         * 需要注意的是： 1) zip 组合事件的过程就是分别从发射器A和发射器B各取出一个事件来组合，
         * 并且一个事件只能被使用一次，组合的顺序是严格按照事件发送的顺序来进行的，
         * 所以上面截图中，可以看到，1永远是和A 结合的，2永远是和B结合的。
         *
         * 2) 最终接收器收到的事件数量是和发送器发送事件最少的那个发送器的发送事件数目相同，
         * 所以如截图中，5很孤单，没有人愿意和它交往，孤独终老的单身狗。
         */
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            /**
             * 这个我们我们Map里面的参数一个Function接口对象，
             * 我们要做的就是从这个Function接受一个Integer类型的参数，
             * 返回一下函数加工之后的String参数
             *
             * 是的，map基本作用就是将一个Observable通过某种函数关系，转换为另一种Observable，
             * 上面例子中就是把我们的Integer数据变成了String类型。
             * 从Log日志显而易见。
             * @param integer
             * @return
             * @throws Exception
             */
            @Override
            public String apply(Integer integer) throws Exception {
                return "This is result " + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                mRxOperatorsText.append("accept : " + s + "\n");
                Log.e(TAG, "accept : " + s + "\n");
            }
        });
    }

}