package com.frewen.android.demo.performance

import android.util.Log
import java.util.*

/**
 * @filename: LaunchTimeRecord
 * @introduction:
 *
 * 我们关于页面的性能冷启动的性能，我们可以学习一下美团
 * https://tech.meituan.com/2018/07/12/autospeed.html
 *
 * @author: Frewen.Wong
 * @time: 2020/9/16 15:41
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
object LaunchTimeRecord {
    private val recordMap: MutableMap<String, Long?> = HashMap()
    
    /**
     * 开始启动事件记录
     */
    fun startRecord(sceneName: String) {
        val startTime = System.currentTimeMillis()
        recordMap[sceneName] = startTime
    }
    
    fun endRecord(sceneName: String?) {
        if (recordMap.containsKey(sceneName)) {
            Log.w("LaunchTimeRecord", "You Record Cost:" + (System.currentTimeMillis() - recordMap[sceneName]!!))
            recordMap.remove(sceneName)
        } else {
            Log.w("LaunchTimeRecord", "You should start Record First")
        }
    }
}