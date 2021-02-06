package com.frewen.demo.library.ktx.extention

import androidx.recyclerview.widget.RecyclerView

/**
 * @filename: RecyclerViewExtention
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/10/18 17:17
 * @version 1.0.0
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
fun RecyclerView.init(
        layoutManger: RecyclerView.LayoutManager,
        bindAdapter: RecyclerView.Adapter<*>,
        isScroll: Boolean = true
): RecyclerView {
    layoutManager = layoutManger
    setHasFixedSize(true)
    adapter = bindAdapter
    isNestedScrollingEnabled = isScroll
    return this
}