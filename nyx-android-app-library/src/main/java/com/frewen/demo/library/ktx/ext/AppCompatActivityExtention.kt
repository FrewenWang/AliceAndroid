package com.frewen.demo.library.ktx.ext

import androidx.appcompat.app.AppCompatActivity

/**
 * 给AppCompatActivity添加扩展函数
 */
fun AppCompatActivity.showMessageDialog(
        message: String,
        title: String = "温馨提示",
        positiveButtonText: String = "确定",
        positiveAction: () -> Unit = {},
        negativeButtonText: String = "",
        negativeAction: () -> Unit = {}
) {




}