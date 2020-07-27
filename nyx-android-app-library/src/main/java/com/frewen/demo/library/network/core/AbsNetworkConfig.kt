package com.frewen.demo.library.network.core

import android.app.Application
import com.frewen.demo.library.network.env.Env

/**
 * @filename: INetworkConfig
 * @introduction: 网络请求的配置接口
 * @author: Frewen.Wong
 * @time: 2020/5/13 20:03
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
interface AbsNetworkConfig {
    /**
     * 获取APP的版本名称
     */
    fun getAppVersionName(): String

    /**
     * 获取APP的版本号
     */
    fun getAppVersionCode(): Int

    /**
     * 判断是否是调试模式
     */
    fun isDebug(): Boolean

    /**
     * 获取ApplicationContext
     */
    fun getAppContext(): Application

    /**
     * 切换程序的运行环境
     */
    fun switchProgramEnv(): Env

}