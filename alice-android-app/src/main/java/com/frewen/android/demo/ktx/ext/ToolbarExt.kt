package com.frewen.android.demo.ktx.ext

import android.content.Context
import androidx.appcompat.widget.Toolbar
import com.frewen.android.demo.R
import com.frewen.android.demo.utils.AppThemeUtil
import com.frewen.aura.toolkits.ktx.ext.toHtml

/**
 * @filename: RecyclerViewExtention
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/10/18 17:17
 * @version 1.0.0
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
fun Toolbar.init(context: Context, titleStr: String = ""): Toolbar {
    setBackgroundColor(AppThemeUtil.getThemeColor(context))
    title = titleStr
    return this
}

fun Toolbar.initWithClose(
    titleStr: String = "",
    backImg: Int = R.drawable.ic_arrow_left_line,
    onBack: (toolbar: Toolbar) -> Unit
): Toolbar {
    title = titleStr.toHtml()
    setNavigationIcon(backImg)
    setNavigationOnClickListener { onBack.invoke(this) }
    return this
}

