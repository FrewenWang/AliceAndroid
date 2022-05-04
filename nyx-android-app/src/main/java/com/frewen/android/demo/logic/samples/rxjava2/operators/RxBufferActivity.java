package com.frewen.android.demo.logic.samples.rxjava2.operators;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.frewen.android.demo.R;
import com.frewen.demo.library.ui.activity.BaseToolBarActivity;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class RxBufferActivity extends BaseToolBarActivity {
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
        /**
         * buffer buffer操作符接受两个参数，buffef(count,skip)
         * 作用是将 Observable 中的数据按 skip (步长) 分成最大不超过count的buffer ，
         * 然后生成一个 Observable 。也许你还不太理解，我们可以通过我们的示例图和示例代码来进一步深化它。
         */
        Observable.just(1, 2, 3, 4, 5)
                .buffer(3, 2)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        mRxOperatorsText.append("buffer size : " + integers.size() + "n");
                        Log.e(TAG, "buffer size : " + integers.size() + "n");
                        mRxOperatorsText.append("buffer value : ");
                        Log.e(TAG, "buffer value : ");
                        for (Integer i : integers) {
                            mRxOperatorsText.append(i + "");
                            Log.e(TAG, i + "");
                        }
                        mRxOperatorsText.append("n");
                        Log.e(TAG, "n");
                    }
                });
        // 如图，我们把1,2,3,4,5依次发射出来，经过buffer 操作符，其中参数 skip 为3， count 为2，
        // 而我们的输出 依次是 123，345，5。显而易见，我们 buffer 的第一个参数是count，代表最大取值，
        // 在事件足够的时候，一般都是取count个值，然后每天跳过skip个事件。其实看 Log 日志，我相信大家都明白了。
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