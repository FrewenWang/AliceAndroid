package com.frewen.android.demo.business.model

/**
 * @filename: ListDataStateWrapper
 * @author: Frewen.Wong
 * @time: 3/20/21 6:20 PM
 * @version: 1.0.0
 * @introduction: 列表数据来封装其UI状态的逻辑
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
data class ListDataStateWrapper<T>(
    //是否请求成功
    val isSuccess: Boolean,
    //错误消息 isSuccess为false才会有
    val errMessage: String = "",
    //是否为刷新
    val isRefresh: Boolean = false,
    //是否为空
    val isEmpty: Boolean = false,
    //是否还有更多
    val hasMore: Boolean = false,
    //是第一页且没有数据
    val isFirstEmpty: Boolean = false,
    //列表数据
    val listData: ArrayList<T> = arrayListOf()
)
