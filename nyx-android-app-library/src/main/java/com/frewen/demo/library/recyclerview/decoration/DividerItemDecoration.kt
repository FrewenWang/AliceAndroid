package com.frewen.demo.library.recyclerview.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @filename: DividerItemDecoration
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/10/18 19:34
 * @version 1.0.0
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class DividerItemDecoration(private val leftRight: Int, private val topBottom: Int, private val firstNeedTop: Boolean = true) : RecyclerView.ItemDecoration() {

    /**
     * 计算RecyclerView的每个Item的分割线
     * 也就是计算每个Item的偏移量
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        val layoutManager = parent.layoutManager as LinearLayoutManager

        when (layoutManager!!.orientation) {
            LinearLayoutManager.VERTICAL -> {
                // 返回给定子视图对应的适配器位置。
                if (parent.getChildAdapterPosition(view) == layoutManager.itemCount - 1) {
                    // 如果Item位于Adapter的最后一项
                    outRect.bottom = topBottom
                }
                //
                if (!firstNeedTop && parent.getChildAdapterPosition(view) == 0) {
                    outRect.top = 0
                } else {
                    outRect.top = topBottom
                }
                outRect.left = leftRight
                outRect.right = leftRight
            }

            LinearLayoutManager.HORIZONTAL -> {
                //最后一项需要right
                if (parent.getChildAdapterPosition(view) != layoutManager.itemCount - 1) {
                    outRect.right = leftRight
                }
                outRect.top = topBottom
                outRect.left = 0
                outRect.bottom = topBottom
            }
        }
    }
}