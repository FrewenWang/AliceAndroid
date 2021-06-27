package com.frewen.android.demo.ktx.ext

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * @filename: SwipeRefreshLayoutExt
 * @author: Frewen.Wong
 * @time: 2021/6/27 18:56
 * @version: 1.0.0
 * @introduction:  Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
/**
 * 初始化 SwipeRefreshLayout
 */
fun SwipeRefreshLayout.init(onRefreshListener: () -> Unit) {
    this.run {
        setOnRefreshListener {
            onRefreshListener.invoke()
        }
        // 设置主题颜色
        // setColorSchemeColors(SettingUtil.getColor(appContext))
    }
}