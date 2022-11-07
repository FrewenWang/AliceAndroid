package com.frewen.android.demo.views.behavior

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.frewen.android.demo.R
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.max
import kotlin.math.min


/**
 * @filename: AppbarZoomBehavior
 * @introduction:
 *  自定义Behavior实现AppBarLayout越界弹性效果
 *  继承AppBarLayout.Behavior
 *  AppBarLayout有一个默认的Behavior，即AppBarLayout.Behavior，AppBarLayout.Behavior已注解的方式设置给AppBarLayout。
 * @author: Frewen.Wong
 * @time: 2020/9/5 17:25
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class AppbarZoomBehavior(context: Context, attrs: AttributeSet) : AppBarLayout.Behavior(context, attrs) {
    private var valueAnimator: ValueAnimator? = null
    private var mLastBottom: Int = 0
    private var mScaleValue: Float = 0.0f
    private var mTotalDy: Float = 0.0f
    private var isAnimate: Boolean = false
    private var mImageViewHeight: Int = 0
    private var mImageView: ImageView? = null
    private val MAX_ZOOM_HEIGHT = 500f //放大最大高度


    /**
     * AppBarHeight是一个私有变量
     */
    private var mAppBarHeight: Int = 0

    /**
     * AppBarLayout布局时调用
     * parent这个View的父控件是CoordinatorLayout协作布局
     * abl：AppBarLayout   使用此Behavior的AppBarLayout
     * @param layoutDirection 布局方向
     * @return 返回true表示子View重新布局，返回false表示请求默认布局
     */
    override fun onLayoutChild(parent: CoordinatorLayout, abl: AppBarLayout, layoutDirection: Int): Boolean {
        val handled = super.onLayoutChild(parent, abl, layoutDirection)
        initView(abl)
        return handled
    }


    /**
     * 当CoordinatorLayout的子View尝试发起嵌套滚动时调用
     *
     * @param parent  父布局CoordinatorLayout
     * @param child   使用此Behavior的AppBarLayout
     * @param directTargetChild  directTargetChild CoordinatorLayout的子View，或者是包含嵌套滚动操作的目标View
     * @param target        target 发起嵌套滚动的目标View(即AppBarLayout下面的ScrollView或RecyclerView)
     * @param nestedScrollAxes      nestedScrollAxes 嵌套滚动的方向
     * @param type                  返回true表示接受滚动
     * @return
     */
    override fun onStartNestedScroll(parent: CoordinatorLayout, child: AppBarLayout, directTargetChild: View, target: View, nestedScrollAxes: Int, type: Int): Boolean {
        isAnimate = true
        return true
    }

    /**
     * 当嵌套滚动已由CoordinatorLayout接受时调用
     *
     * @param coordinatorLayout 父布局CoordinatorLayout
     * @param child 使用此Behavior的AppBarLayout
     * @param directTargetChild CoordinatorLayout的子View，或者是包含嵌套滚动操作的目标View
     * @param target 发起嵌套滚动的目标View(即AppBarLayout下面的ScrollView或RecyclerView)
     * @param axes 嵌套滚动的方向
     */
    override fun onNestedScrollAccepted(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, directTargetChild: View, target: View, axes: Int, type: Int) {
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, axes, type)
    }

    /**
     * 当CoordinatorLayout的子View尝试发起嵌套滚动时调用
     *
     * @param coordinatorLayout 父布局CoordinatorLayout
     * @param child 使用此Behavior的AppBarLayout
     * @param target CoordinatorLayout的子View，或者是包含嵌套滚动操作的目标View
     * @param dx
     * @param dy
     * @param consumed
     * @param type
     */
    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    }

    /**
     * 嵌套滚动时调用
     *
     * @param coordinatorLayout 父布局CoordinatorLayout
     * @param child 使用此Behavior的AppBarLayout
     * @param target 发起嵌套滚动的目标View(即AppBarLayout下面的ScrollView或RecyclerView)
     * @param dxConsumed 由目标View滚动操作消耗的水平像素数
     * @param dyConsumed 由目标View滚动操作消耗的垂直像素数
     * @param dxUnconsumed 由用户请求但是目标View滚动操作未消耗的水平像素数
     * @param dyUnconsumed 由用户请求但是目标View滚动操作未消耗的垂直像素数
     */
    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int, consumed: IntArray) {
        // 向上滑动
        if (null != mImageView && child.bottom >= mAppBarHeight && dyConsumed < 0 && type == ViewCompat.TYPE_TOUCH) {
            zoomHeaderImageView(child, dyConsumed)
        } else {
            if (mImageView != null && child.bottom > mAppBarHeight && dyConsumed > 0 && type == ViewCompat.TYPE_TOUCH) {
                consumed[1] = dyConsumed
                zoomHeaderImageView(child, dyConsumed)
            } else {
                if (valueAnimator == null || !valueAnimator!!.isRunning) {
                    super.onNestedPreScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, consumed, type)
                }
            }
        }
    }

    private fun zoomHeaderImageView(child: AppBarLayout, dyConsumed: Int) {
        mTotalDy += -dyConsumed.toFloat()
        mTotalDy = min(mTotalDy, MAX_ZOOM_HEIGHT)
        mScaleValue = max(1f, 1f + mTotalDy / MAX_ZOOM_HEIGHT)
        ViewCompat.setScaleX(mImageView, mScaleValue)
        ViewCompat.setScaleY(mImageView, mScaleValue)
        mLastBottom = mAppBarHeight + (mImageViewHeight / 2 * (mScaleValue - 1)) as Int
        child.bottom = mLastBottom
    }

    /**
     * 当嵌套滚动的子View准备快速滚动时调用
     *
     * @param coordinatorLayout 父布局CoordinatorLayout
     * @param child 使用此Behavior的AppBarLayout
     * @param target 发起嵌套滚动的目标View(即AppBarLayout下面的ScrollView或RecyclerView)
     * @param velocityX 水平方向的速度
     * @param velocityY 垂直方向的速度
     * @return 如果Behavior消耗了快速滚动返回true
     */
    override fun onNestedPreFling(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, target: View, velocityX: Float, velocityY: Float): Boolean {
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)
    }

    /**
     * 当嵌套滚动的子View快速滚动时调用
     *
     * @param coordinatorLayout 父布局CoordinatorLayout
     * @param child 使用此Behavior的AppBarLayout
     * @param target 发起嵌套滚动的目标View(即AppBarLayout下面的ScrollView或RecyclerView)
     * @param velocityX 水平方向的速度
     * @param velocityY 垂直方向的速度
     * @param consumed 如果嵌套的子View消耗了快速滚动则为true
     * @return 如果Behavior消耗了快速滚动返回true
     */
    override fun onNestedFling(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed)
    }

    /**
     * 当定制滚动时调用
     *
     * @param coordinatorLayout 父布局CoordinatorLayout
     * @param abl 使用此Behavior的AppBarLayout
     * @param target 发起嵌套滚动的目标View(即AppBarLayout下面的ScrollView或RecyclerView)
     */
    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, abl: AppBarLayout, target: View, type: Int) {
        recovery(abl)
        super.onStopNestedScroll(coordinatorLayout, abl, target, type)
    }

    private fun recovery(abl: AppBarLayout) {
        if (mTotalDy > 0) {
            mTotalDy = 0f
            if (isAnimate) {
                valueAnimator = ValueAnimator.ofFloat(mScaleValue, 1f).setDuration(220)
                valueAnimator.run {
                    this?.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
                        val value = animation.animatedValue as Float
                        ViewCompat.setScaleX(mImageView, value)
                        ViewCompat.setScaleY(mImageView, value)
                        abl.bottom = (mLastBottom - (mLastBottom - mAppBarHeight) * animation.animatedFraction) as Int
                    })
                    this?.start()
                }
            } else {
                ViewCompat.setScaleX(mImageView, 1f)
                ViewCompat.setScaleY(mImageView, 1f)
                abl.bottom = mAppBarHeight
            }
        }
    }


    /**
     * 这里我们是在AppBarLayout布局的时候来进行获取ImageView的宽度和高度
     */
    private fun initView(abl: AppBarLayout) {
        // 默认情况下，在绘制之前将子布局剪切到其边界。
        abl.clipChildren = false
        mAppBarHeight = abl.height

        mImageView = abl.findViewById(R.id.iv_bg)

        if (mImageView != null) {
            mImageViewHeight = mImageView!!.height
        }
    }
}