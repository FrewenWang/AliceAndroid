package com.frewen.android.demo.business.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class DemoModel(
    var author: String,     // 作者
    var name: String,       // demo程序的名称
    var desc: String,       // Demo程序的描述信息
) : Parcelable, BaseModel()
