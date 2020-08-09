package com.frewen.android.demo.views.behavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.frewen.android.demo.R;

/**
 * @filename: SimpleViewBehavior
 * @introduction: 使用CoordinatorLayout时，会在xml文件中用它作为根布局，
 * 并给相应的子View添加一个类似app:layout_behavior="@string/appbar_scrolling_view_behavior"的属性，当然属性值也可以是其它的。
 * 进一步可以发现@string/appbar_scrolling_view_behavior的值是android.support.design.widget.AppBarLayout$ScrollingViewBehavior，
 * 不就是support包下一个类的路径嘛！玄机就在这里，通过CoordinatorLayout之所以可以实现炫酷的交互效果，Behavior功不可没。
 * 既然如此，我们也可以自定义Behavior，来定制我们想要的效果。
 * @author: Frewen.Wong
 * @time: 2020/8/9 16:40
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class SimpleViewBehavior extends CoordinatorLayout.Behavior<View> {

    private static final int UNSPECIFIED_INT = Integer.MAX_VALUE;
    private static final float UNSPECIFIED_FLOAT = Float.MAX_VALUE;


    private static final int DEPEND_TYPE_HEIGHT = 0;
    private static final int DEPEND_TYPE_WIDTH = 1;
    private static final int DEPEND_TYPE_X = 2;
    private static final int DEPEND_TYPE_Y = 3;

    private int mDependViewId = 0;              //默认没有依赖对象
    private int mDependType = DEPEND_TYPE_Y;    //默认按照y方向变化
    private int mDependTargetX;                 //X方向的允许最大距离(影响动画percent)
    private int mDependTargetY;                 //Y方向的允许最大距离(影响动画percent)
    private int mDependTargetWidth;             //依赖控件起始最大宽度(影响动画percent)
    private int mDependTargetHeight;            //依赖控件起始最大高度(影响动画percent)
    private int targetX;
    private int targetY;
    private int targetWidth;
    private int targetHeight;
    private int targetBackgroundColor;
    private float targetAlpha;
    private float targetRotateX;
    private float targetRotateY;
    private int mAnimationId = 0;               //自定义动画id(xml文件定义动画)

    private boolean isPrepared;

    public SimpleViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SimpleViewBehavior);
        mDependViewId = a.getResourceId(R.styleable.SimpleViewBehavior_svb_dependOn, mDependViewId);
        mDependType = a.getInt(R.styleable.SimpleViewBehavior_svb_dependType, mDependType);
        mDependTargetX = a.getDimensionPixelOffset(R.styleable.SimpleViewBehavior_svb_dependTargetX, UNSPECIFIED_INT);
        mDependTargetY = a.getDimensionPixelOffset(R.styleable.SimpleViewBehavior_svb_dependTargetY, UNSPECIFIED_INT);
        mDependTargetWidth = a.getDimensionPixelOffset(R.styleable.SimpleViewBehavior_svb_dependTargetWidth, UNSPECIFIED_INT);
        mDependTargetHeight = a.getDimensionPixelOffset(R.styleable.SimpleViewBehavior_svb_dependTargetHeight, UNSPECIFIED_INT);
        targetX = a.getDimensionPixelOffset(R.styleable.SimpleViewBehavior_svb_targetX, UNSPECIFIED_INT);
        targetY = a.getDimensionPixelOffset(R.styleable.SimpleViewBehavior_svb_targetY, UNSPECIFIED_INT);
        targetWidth = a.getDimensionPixelOffset(R.styleable.SimpleViewBehavior_svb_targetWidth, UNSPECIFIED_INT);
        targetHeight = a.getDimensionPixelOffset(R.styleable.SimpleViewBehavior_svb_targetHeight, UNSPECIFIED_INT);
        targetBackgroundColor = a.getColor(R.styleable.SimpleViewBehavior_svb_targetBackgroundColor, UNSPECIFIED_INT);
        targetAlpha = a.getFloat(R.styleable.SimpleViewBehavior_svb_targetAlpha, UNSPECIFIED_FLOAT);
        targetRotateX = a.getFloat(R.styleable.SimpleViewBehavior_svb_targetRotateX, UNSPECIFIED_FLOAT);
        targetRotateY = a.getFloat(R.styleable.SimpleViewBehavior_svb_targetRotateY, UNSPECIFIED_FLOAT);
        mAnimationId = a.getResourceId(R.styleable.SimpleViewBehavior_svb_animation, mAnimationId);
        a.recycle();
    }

    /**
     * 确定使用Behavior的View要依赖的View的类型
     *
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == mDependViewId;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        // 该方法会在滑动的时候一直回调,但只需要初始化一次
        if (!isPrepared) {
            prepare(parent, child, dependency);
        }
        updateView(child, dependency);
        return false;
    }


    private void prepare(CoordinatorLayout parent, View child, View dependency) {

    }


    private void updateView(View child, View dependency) {

    }


}
