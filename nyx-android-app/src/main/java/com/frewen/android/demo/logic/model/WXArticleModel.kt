package com.frewen.android.demo.logic.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 微信公众号的数据实体
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class WXArticleModel(var children: List<String> = listOf(),
                          var courseId: Int = 0,
                          var id: Int = 0,
                          var name: String = "",
                          var order: Int = 0,
                          var parentChapterId: Int = 0,
                          var userControlSetTop: Boolean = false,
                          var visible: Int = 0) : Parcelable
