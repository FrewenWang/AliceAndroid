package com.frewen.demo.library.ktx.ext

import androidx.appcompat.widget.Toolbar

/**
 * @filename: ToolBarEntention
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/20 22:18
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
fun Toolbar.init(titleStr: String = ""): Toolbar {
    title = titleStr
    return this
}
