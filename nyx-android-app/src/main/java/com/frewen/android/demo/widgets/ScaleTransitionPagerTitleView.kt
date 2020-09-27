package com.frewen.android.demo.widgets

import android.content.Context
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView

/**
 * @filename: ScaleTransitionPagerTitleView
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/27 18:00
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class ScaleTransitionPagerTitleView(context: Context) : ColorTransitionPagerTitleView(context) {

    var minScale = 0.7f
    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
        super.onEnter(index, totalCount, enterPercent, leftToRight)
        scaleX = minScale + (1.0f - minScale) * enterPercent
        scaleY = minScale + (1.0f - minScale) * enterPercent

    }

    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
        super.onLeave(index, totalCount, leavePercent, leftToRight)    // 实现颜色渐变
        scaleX = 1.0f + (minScale - 1.0f) * leavePercent
        scaleY = 1.0f + (minScale - 1.0f) * leavePercent
    }
}