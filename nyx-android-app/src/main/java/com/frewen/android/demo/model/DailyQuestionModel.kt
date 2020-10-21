package com.frewen.android.demo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @filename: DailyQuestionModel
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/10/18 16:32
 * @version 1.0.0
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@Parcelize
data class DailyQuestionModel(
        var apkLink: String,
        var author: String,//作者
        var chapterId: Int,
        var chapterName: String,
        var collect: Boolean,//是否收藏
        var courseId: Int,
        var desc: String,
        var envelopePic: String,
        var fresh: Boolean,
        var id: Int,
        var link: String,
        var niceDate: String,
        var origin: String,
        var prefix: String,
        var projectLink: String,
        var publishTime: Long,
        var superChapterId: Int,
        var superChapterName: String,
        var shareUser: String,
        var title: String,
        var type: Int,
        var userId: Int,
        var visible: Int,
        var zan: Int
) : Parcelable
