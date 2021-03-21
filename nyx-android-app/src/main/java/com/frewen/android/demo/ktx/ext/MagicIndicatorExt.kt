package com.frewen.android.demo.extention

import android.content.Context
import android.graphics.Color
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.viewpager2.widget.ViewPager2
import com.frewen.android.demo.app.NyxAndroidApp
import com.frewen.android.demo.widgets.ScaleTransitionPagerTitleView
import com.frewen.aura.toolkits.ktx.ext.toHtml
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

/**
 * @filename: MagicIndicatorExt
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/27 17:47
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
fun MagicIndicator.bindViewPager2(
        viewPager2: ViewPager2,
        mDataList: ArrayList<String> = arrayListOf(),
        mStringList: ArrayList<String> = arrayListOf(),
        action: (index: Int) -> Unit = {}) {
    
    val commonNavigator = CommonNavigator(NyxAndroidApp.getInstance(NyxAndroidApp::class.java))
    // 实例化一个匿名对象，实现CommonNavigatorAdapter抽象类
    commonNavigator.adapter = object : CommonNavigatorAdapter() {
        override fun getCount(): Int {
            return if (mDataList.isEmpty()) {
                mStringList.size
            } else {
                mDataList.size
            }
        }
        
        override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
            /**
             * 整体作用功能和run函数很像，唯一不同点就是它返回的值是对象本身，
             * 而run函数是一个闭包形式返回，返回的是最后一行的值。
             * 正是基于这一点差异它的适用场景稍微与run函数有点不一样。
             * apply一般用于一个对象实例初始化的时候，需要对对象中的属性进行赋值。
             */
            return ScaleTransitionPagerTitleView(NyxAndroidApp.getInstance(NyxAndroidApp::class.java)).apply {
                text = if (mDataList.size != 0) {
                    mDataList[index].toHtml()
                } else {
                    mStringList[index].toHtml()
                }
                textSize = 17f
                normalColor = Color.WHITE
                selectedColor = Color.WHITE
                setOnClickListener {
                    viewPager2.currentItem = index
                    action.invoke(index)
                }
            }
        }
        
        override fun getIndicator(context: Context?): IPagerIndicator {
            return LinePagerIndicator(context).apply {
                mode = LinePagerIndicator.MODE_EXACTLY
                //线条的宽高度
                lineHeight = UIUtil.dip2px(NyxAndroidApp.getInstance(NyxAndroidApp::class.java), 3.0).toFloat()
                lineWidth = UIUtil.dip2px(NyxAndroidApp.getInstance(NyxAndroidApp::class.java), 30.0).toFloat()
                //线条的圆角
                roundRadius = UIUtil.dip2px(NyxAndroidApp.getInstance(NyxAndroidApp::class.java), 6.0).toFloat()
                startInterpolator = AccelerateInterpolator()
                endInterpolator = DecelerateInterpolator(2.0f)
                //线条的颜色
                setColors(Color.WHITE)
            }
        }
    }
    
    this.navigator = commonNavigator
    
    viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            this@bindViewPager2.onPageSelected(position)
            action.invoke(position)
        }
    
        override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            this@bindViewPager2.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }
    
        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            this@bindViewPager2.onPageScrollStateChanged(state)
        }
    })
    
}