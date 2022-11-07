package com.frewen.android.demo.business.samples.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.frewen.android.demo.R;

/**
 * CoordinatorLayout 遵循 Material风格，结合 AppbarLayout, CollapsingToolbarLayout 等可产生各种炫酷的效果
 */
public class CoordinatorLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);


        initCoordinatorLayout();

        initAppBar();
    }

    /**
     * AppBarLayout 是 LinearLayout 的子类，
     * 必须在它的子view 上设置app:layout_scrollFlags属性或者是在代码中调用setScrollFlags()设置这个属性。
     */
    private void initAppBar() {

    }

    /**
     * CoordinatorLayout:协调者布局，它是 support.design 包中的控件。
     * 简单来说，CoordinatorLayout 是用来协调其子view 并以触摸影响布局的形式产生动画效果的一个super-powered FrameLayout，
     * 其典型的子 View 包括：FloatingActionButton，SnackBar。注意：CoordinatorLayout 是一个顶级父View。
     */
    private void initCoordinatorLayout() {

    }
}