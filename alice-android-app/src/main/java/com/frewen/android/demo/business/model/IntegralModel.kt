package com.frewen.android.demo.business.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 我的页面的积分相关的数据
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class IntegralModel(
    //当前积分
    var coinCount: Int,
    // 排名
    var rank: Int,
    var userId: Int,
    var username: String
) : Parcelable