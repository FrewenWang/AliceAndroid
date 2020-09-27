package com.frewen.android.demo.extention

import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.getActionButton
import com.frewen.aura.ui.utils.ColorUtils

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


    MaterialDialog(this).cancelable(false).show {
        title(text = title)
        message(text = message)
        positiveButton(text = positiveButtonText) {
            positiveAction.invoke()
        }
        if (negativeButtonText.isNotEmpty()) {
            negativeButton(text = negativeButtonText) {
                negativeAction.invoke()
            }
        }
        getActionButton(WhichButton.POSITIVE).updateTextColor(ColorUtils.getPrimaryColor(this@showMessageDialog))
        getActionButton(WhichButton.NEGATIVE).updateTextColor(ColorUtils.getPrimaryColor(this@showMessageDialog))
    }

}