package com.frewen.android.demo.logic.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @filename: ArticleResponse
 * @author: Frewen.Wong
 * @time: 2/12/21 3:44 PM
 * @version: 1.0.0
 * @introduction:  首页的文章的内容的实体
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class ArticleBean(
        var apkLink: String,
        var author: String,
        var chapterId: Int,
        var chapterName: String,
        var collect: Boolean,
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
        var tags: List<ArticleTagsBean>,
        var title: String,
        var type: Int,
        var userId: Int,
        var visible: Int,
        var zan: Int) : Parcelable
