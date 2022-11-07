package com.frewen.android.demo.business.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class NavigationModel(
    var articles: ArrayList<ArticleModel>,
    var cid: Int,
    var name: String
) : Parcelable