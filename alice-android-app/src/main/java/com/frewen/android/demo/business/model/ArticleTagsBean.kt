package com.frewen.android.demo.business.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @filename: ArticleTagsBean
 * @author: Frewen.Wong
 * @time: 2/12/21 3:47 PM
 * @version: 1.0.0
 * @introduction:  文章的标签的实体
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class ArticleTagsBean(var name: String, var url: String) : Parcelable
