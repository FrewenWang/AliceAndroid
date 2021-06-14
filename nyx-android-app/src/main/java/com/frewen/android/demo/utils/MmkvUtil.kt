package com.frewen.android.demo.utils

import com.tencent.mmkv.MMKV

/**
 * @filename: MmkvUtil
 * @author: Frewen.Wong
 * @time: 2021/6/14 10:29
 * @version: 1.0.0
 * @introduction:  Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
object MmkvUtil {
    
    /**
     * 是否是第一次登陆
     */
    fun isFirstLaunch(): Boolean {
        val kv = MMKV.mmkvWithID("app")
        return kv!!.decodeBool("firstLaunch", true)
    }
}