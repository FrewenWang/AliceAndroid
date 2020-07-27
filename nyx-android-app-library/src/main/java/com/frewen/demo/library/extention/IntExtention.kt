package com.frewen.demo.library.extention

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @filename: IntExtention
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/7/24 07:56
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */


/**
 * Int解析xml布局
 *
 * @param parent 父布局
 * @param attachToRoot 是否依附到父布局
 */
fun Int.inflate(parent: ViewGroup, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(parent.context).inflate(this, parent, attachToRoot)
}
