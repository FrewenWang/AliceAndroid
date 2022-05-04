package com.frewen.android.demo.utils

import android.text.TextUtils
import com.frewen.android.demo.logic.model.UserInfo
import com.google.gson.Gson
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
     * 获取保存的账户信息。返回账号的信息的Json数据
     */
    fun getUser(): UserInfo? {
        val kv = MMKV.mmkvWithID("app")
        val userStr = kv!!.decodeString("user")
        return if (TextUtils.isEmpty(userStr)) {
            null
        } else {
            Gson().fromJson(userStr, UserInfo::class.java)
        }
    }

    /**
     * 是否是第一次登陆
     */
    fun isFirstLaunch(): Boolean {
        val kv = MMKV.mmkvWithID("app")
        return kv!!.decodeBool("firstLaunch", true)
    }

    /**
     * 是否已经登录
     */
    fun isLogin(): Boolean {
        val kv = MMKV.mmkvWithID("app")
        return kv!!.decodeBool("login", false)
    }
}