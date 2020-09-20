package com.frewen.android.demo.samples.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.frewen.android.demo.R;
import com.frewen.aura.framework.ui.BaseButterKnifeActivity;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @filename: ViewDemoActivity
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2019-05-15 08:17
 *         Copyright ©2019 Frewen.Wong. All Rights Reserved.
 */
public class ViewDemoActivity extends BaseButterKnifeActivity {
    private static final String TAG = "T:ViewDemoActivity";

    @BindView(R.id.root)
    RelativeLayout root;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_view_demo;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    protected void initView() {
        //在onCreate()、onStrart()、onResume()方法中会返回0，
        // 这是因为当前activity所代表的界面还没显示出来没有添加到WindowPhone的DecorView上
        // 或要获取的view没有被添加到DecorView上或者该View的visibility属性为gone
        // 或者该view的width或height真的为0
        // 所以只有上述条件都不成立时才能得到非0的width和height。

//        getViewInfo("从Resume()方法获取View信息", mLinearLayout);
//        getViewInfo("从Resume()方法获取View信息", mTextView);
//
//        mLinearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                mLinearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                getViewInfo("从OnGlobalLayoutListener()方法获取View信息", mLinearLayout);
//                getViewInfo("从OnGlobalLayoutListener()方法获取View信息", mTextView);
//
//            }
//        });

        // 我们给rootView来添加一个视图变化的监听。来监听当前的视频
        root.getViewTreeObserver().addOnGlobalLayoutListener(() -> hookViews(root, 0));

    }

    private void hookViews(RelativeLayout root, int i) {

    }

    @OnClick({R.id.button})
    public void clickAddButton(View view) {
        /**
         * 即root传参为空，与上面第2种情况对应，所以此时布局根View的android:layout_xx属性都被忽略了。
         * 也就是相当于并没有给TextView设置宽高，所以只能按默认的TextView大小显示了。
         */
//        View insideView = LayoutInflater.from(ViewDemoActivity.this).inflate(R.layout.item_list_textview, null);
//        root.addView(insideView);
        /**
         * 如果我们想让这个TextView能设置的android:layout_xx属性都能生效的话，我们需要使用下面的方式
         */
//        View insideView = LayoutInflater.from(ViewDemoActivity.this).inflate(R.layout.item_list_textview, root);

        /**
         * 如果我们想让这个TextView能设置的android:layout_xx属性都能生效的话，我们需要使用下面的方式
         */
        View insideView = LayoutInflater.from(ViewDemoActivity.this).inflate(R.layout.item_list_textview, root, false);
        root.addView(insideView);
    }

    /**
     * 获取View的信息
     *
     * @param view
     */
    private void getViewInfo(String testInfo, View view) {
        Log.d(TAG, "FMsg:getViewInfo() called with: testInfo = [" + testInfo + "], view = [" + view + "]");
        int left = view.getLeft();
        int right = view.getRight();
        int top = view.getTop();
        int bottom = view.getBottom();

        int width = view.getWidth();
        int height = view.getHeight();
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        Log.i(TAG, "FMsg:getViewInfo() :width =  " + width + ",height = " + height
                + ",measuredWidth = " + measuredWidth + ",measuredHeight = " + measuredHeight);
        Log.i(TAG, "FMsg:getViewInfo() :left =  " + left + ",right = " + right
                + ",top=" + top + ",bottom= " + bottom);
    }

}
