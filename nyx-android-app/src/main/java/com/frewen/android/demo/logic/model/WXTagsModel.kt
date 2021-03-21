package com.frewen.android.demo.logic.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 微信公众号的Tags的数据实体
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class TagsResponse(var name: String, var url: String) : Parcelable
