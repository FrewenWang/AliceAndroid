package com.frewen.demo.library.ktx.ext

import android.view.View
import android.widget.TextView
import com.frewen.android.demo.R
import com.frewen.android.demo.ui.loadstate.EmptyCallback
import com.frewen.android.demo.ui.loadstate.ErrorCallback
import com.frewen.android.demo.ui.loadstate.LoadingStateCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir

/**
 * @filename: LoadingStateServiceExt
 * @author: Frewen.Wong
 * @time: 2021/6/24 08:02
 * @version: 1.0.0
 * @introduction:  Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */

fun loadStateServiceInit(view: View, callback: () -> Unit): LoadService<Any> {
    val loadService = LoadSir.getDefault().register(view) {
        //点击重试时触发的操作
        callback.invoke()
    }
    loadService.showSuccess()
    return loadService
}

/**
 * 设置加载中
 */
fun LoadService<*>.showLoading() {
    this.showCallback(LoadingStateCallback::class.java)
}

/**
 * 设置错误布局
 * @param message 错误布局显示的提示内容
 */
fun LoadService<*>.showError(message: String = "") {
    this.setErrorText(message)
    this.showCallback(ErrorCallback::class.java)
}


fun LoadService<*>.setErrorText(message: String) {
    if (message.isNotEmpty()) {
        this.setCallBack(ErrorCallback::class.java) { _, view ->
            view.findViewById<TextView>(R.id.error_text).text = message
        }
    }
}


/**
 * 设置空布局
 */
fun LoadService<*>.showEmpty() {
    this.showCallback(EmptyCallback::class.java)
}
