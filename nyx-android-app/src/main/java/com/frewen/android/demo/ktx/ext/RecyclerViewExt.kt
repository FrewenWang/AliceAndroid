package com.frewen.android.demo.ktx.ext

import com.frewen.android.demo.NyxApp
import com.frewen.demo.library.recyclerview.DefineLoadMoreView
import com.yanzhenjie.recyclerview.SwipeRecyclerView

/**
 * @filename: RecyclerViewExtention
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/10/18 17:17
 * @version 1.0.0
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */

fun SwipeRecyclerView.addExtFooterView(loadMoreListener: SwipeRecyclerView.LoadMoreListener): DefineLoadMoreView {
    val footerView = DefineLoadMoreView(NyxApp.getInstance(NyxApp::class.java))
    // 给尾部设置颜色
    // footerView.setLoadViewColor(SettingUtil.getOneColorStateList(appContext))
    //设置尾部点击回调
    footerView.setLoadMoreListener(SwipeRecyclerView.LoadMoreListener {
        footerView.onLoading()
        loadMoreListener.onLoadMore()
    })
    this.run {
        //添加加载更多尾部
        addFooterView(footerView)
        setLoadMoreView(footerView)
        //设置加载更多回调
        setLoadMoreListener(loadMoreListener)
    }
    return footerView
}
