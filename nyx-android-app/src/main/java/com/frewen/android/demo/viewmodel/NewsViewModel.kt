package com.frewen.android.demo.viewmodel

import android.app.Application
import com.frewen.demo.library.viewmodel.BaseListViewModel
import javax.inject.Inject

/**
 * @filename: NewsViewModel
 * @introduction: 新闻页面的ViewModel
 * @author: Frewen.Wong
 * @time: 2020/4/14 19:20
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class NewsViewModel @Inject constructor(private val application: Application) : BaseListViewModel(application) {

}