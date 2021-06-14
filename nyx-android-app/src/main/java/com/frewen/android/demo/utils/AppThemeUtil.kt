package com.frewen.android.demo.utils

import android.content.Context
import android.graphics.Color
import android.preference.PreferenceManager
import androidx.core.content.ContextCompat
import com.frewen.android.demo.R
import com.tencent.mmkv.MMKV

/**
 * @filename: Settings
 * @author: Frewen.Wong
 * @time: 2/12/21 3:55 PM
 * @version: 1.0.0
 * @introduction:  Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
object AppThemeUtil {
    
    /**
     * 获取当前主题颜色
     */
    fun getThemeColor(context: Context): Int {
        // 获取偏好设置
        val setting = PreferenceManager.getDefaultSharedPreferences(context)
        val defaultColor = ContextCompat.getColor(context, R.color.colorPrimary)
        val color = setting.getInt("color", defaultColor)
        return if (color != 0 && Color.alpha(color) != 255) {
            defaultColor
        } else {
            color
        }
    }
    
    /**
     * 获取我们设置的列表动画模式
     */
    fun getListAnimMode(): Int {
        val kv = MMKV.mmkvWithID("app")
        //0 关闭动画 1.渐显 2.缩放 3.从下到上 4.从左到右 5.从右到左
        return kv!!.decodeInt("mode", 2)
    }
}