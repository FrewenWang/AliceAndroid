package com.frewen.android.demo.business.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @filename: RecommendTabRespData
 * @introduction: 推荐页面的标题栏的相应数据
 * @author: Frewen.Wong
 * @time: 2020/10/24 16:01
 * @version 1.0.0
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@Parcelize
data class RecommendTabRespData(
    var children: List<String> = listOf(),
    var courseId: Int = 0,
    var id: Int = 0,
    var name: String = "",
    var order: Int = 0,
    var parentChapterId: Int = 0,
    var userControlSetTop: Boolean = false,
    var visible: Int = 0
) : Parcelable
