package com.frewen.android.demo.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

/**
 * @filename: FloatingActionBtnBehavior
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/8/3 11:49
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class FloatingActionBtnBehavior extends FloatingActionButton.Behavior {

    private boolean visible = true;


    /**
     * FloatingActionBtnBehavior
     * @param context
     * @param attrs
     */
    public FloatingActionBtnBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 一定要按照自己的需求返回true，该方法决定了当前控件是否能接收到其内部
     * View(非并非是直接子 View)滑动时的参数；
     * 假设你只涉及到纵向滑动，这里可以根据 nestedScrollAxes 这个参数，进行纵向判断。
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param axes
     * @param type
     * @return
     */
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
            @NonNull FloatingActionButton child,
            @NonNull View directTargetChild,
            @NonNull View target, int axes, int type) {

        //判断如果是垂直滚动则返回true
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    /**
     * 在内层 view 将剩下的滚动消耗完之后调用,可以在这里处理最后剩下的滚动
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     * @param type
     * @param consumed
     */
    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child,
            @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type,
            @NonNull int[] consumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type,
                consumed);
        /// 当向下滑动的时候，并且当前是显示的。那就进行隐藏。
        if (dyConsumed > 0 && visible) {
            visible = false;
            child.animate().scaleX(0).scaleY(0).setInterpolator(new AccelerateInterpolator(3));
        } else if (dyConsumed < 0 && !visible) {
            //// 向上滑动，进行显示
            visible = true;
            child.animate().scaleX(1).scaleY(1).setInterpolator(new DecelerateInterpolator(3));
        }
    }
}
