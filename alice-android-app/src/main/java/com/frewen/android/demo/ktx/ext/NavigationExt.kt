package com.frewen.android.demo.ktx.ext

import androidx.navigation.NavController
import com.frewen.android.demo.utils.MmkvUtil

/**
 * 拦截登录操作，如果没有登录跳转登录，登录过了贼执行你的方法
 */
fun NavController.jumpByLogin(action: (NavController) -> Unit) {
    if (MmkvUtil.isLogin()) {
        action(this)
    } else {
        // this.navigateAction(R.id.action_to_loginFragment)
    }
}