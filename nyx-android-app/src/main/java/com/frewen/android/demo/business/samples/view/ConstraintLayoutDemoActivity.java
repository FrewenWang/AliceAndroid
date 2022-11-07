package com.frewen.android.demo.business.samples.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.frewen.android.demo.R;

/**
 * https://developer.android.google.cn/reference/android/support/constraint/ConstraintLayout
 * 文章参考：https://www.jianshu.com/p/17ec9bd6ca8a
 * 约束布局ConstraintLayout 是一个ViewGroup，可以在Api9以上的Android系统使用它，它的出现主要是为了解决布局嵌套过多的问题，
 * 以灵活的方式定位和调整小部件。从 Android Studio 2.3 起，官方的模板默认使用 ConstraintLayout。
 */
public class ConstraintLayoutDemoActivity extends AppCompatActivity {

    /**
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_layout_demo);


        constraintLayout();


        angleLayout();

        marginLayout();

        goneMarginLayout();

    }

    /**
     * goneMargin
     * goneMargin主要用于约束的控件可见性被设置为gone的时候使用的margin值，属性如下：
     * layout_goneMarginStart
     * layout_goneMarginEnd
     * layout_goneMarginLeft
     * layout_goneMarginTop
     * layout_goneMarginRight
     * layout_goneMarginBottom
     *
     *
     *
     */
    private void goneMarginLayout() {

    }


    /**
     * 边距显示：
     * ConstraintLayout的边距常用属性如下：
     * android:layout_marginStart
     * android:layout_marginEnd
     * android:layout_marginLeft
     * android:layout_marginTop
     * android:layout_marginRight
     * android:layout_marginBottom
     *
     * 跟别的布局没有什么差别，但实际上控件在ConstraintLayout里面要实现margin，
     * 必须先约束该控件在ConstraintLayout里的位置
     *
     * 如果这个控件没有指定在ConstraintLayout里面的位置的话。这个边距属性的不生效的
     */
    private void marginLayout() {

    }

    /**
     * 2、角度约束关系布局
     * 角度定位指的是可以用一个角度和一个距离来约束两个空间的中心。
     *
     * app:layout_constraintCircle                      约束此View的左侧以指定View作为圆心
     * app:layout_constraintCircleAngle="120"（角度）    约束此View以指定圆心的偏移角度
     * app:layout_constraintCircleRadius="150dp"（距离） 约束此View以指定圆心的偏移半径
     */
    private void angleLayout() {

    }

    /**
     * 1、约束关系布局：
     * layout_constraintLeft_toLeftOf       约束此View的左侧在指定View的左侧
     * layout_constraintLeft_toRightOf      约束此View的左侧在指定View的右侧
     * layout_constraintRight_toLeftOf      约束此View的右侧在指定View的左侧
     * layout_constraintRight_toRightOf     约束此View的右侧在指定View的右侧
     * layout_constraintTop_toTopOf         约束此View的顶部在指定View的顶部
     * layout_constraintTop_toBottomOf      约束此View的顶部在指定View的底部
     * layout_constraintBottom_toTopOf              约束此View的底部在指定View的顶部
     * layout_constraintBottom_toBottomOf           约束此View的底部在指定View的底部
     * layout_constraintBaseline_toBaselineOf       约束此View的文本对齐基线在指定View的文本基线对齐
     * layout_constraintStart_toEndOf
     * layout_constraintStart_toStartOf
     * layout_constraintEnd_toStartOf
     * layout_constraintEnd_toEndOf
     */
    private void constraintLayout() {

    }
}