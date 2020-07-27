package com.frewen.demo.library.network.env

/**
 * @filename: AbsProgramEnv
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/5/13 21:37
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
interface AbsProgramEnv {
    /**
     * 获取正式环境的URL
     */
    fun getProdUrl(): String

    /**
     * 获取灰度环境的URL
     */
    fun getPreUrl(): String

    /**
     * 获取测试环境的URL
     */
    fun getTestUrl(): String

    /**
     * 获取开发环境的URL
     */
    fun getDevUrl(): String
}