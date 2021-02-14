package com.frewen.android.demo.utils

import com.tencent.mmkv.MMKV

/**
 * @filename: Settings
 * @author: Frewen.Wong
 * @time: 2/12/21 3:55 PM
 * @version: 1.0.0
 * @introduction:  Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
object SettingsUtils {
    
    /**
     * 获取我们设置的列表动画模式
     */
    fun getListAnimMode(): Int {
        val kv = MMKV.mmkvWithID("app")
        //0 关闭动画 1.渐显 2.缩放 3.从下到上 4.从左到右 5.从右到左
        return kv!!.decodeInt("mode", 2)
    }
}