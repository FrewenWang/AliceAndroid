package com.frewen.demo.library.ktx.ext

import androidx.recyclerview.widget.RecyclerView
import com.yanzhenjie.recyclerview.SwipeRecyclerView

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

fun SwipeRecyclerView.init(
        layoutManger: RecyclerView.LayoutManager,
        bindAdapter: RecyclerView.Adapter<*>,
        isScroll: Boolean = true
): SwipeRecyclerView {
    layoutManager = layoutManger
    setHasFixedSize(true)
    adapter = bindAdapter
    isNestedScrollingEnabled = isScroll
    return this
}