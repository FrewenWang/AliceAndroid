package com.frewen.github.library.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frewen.github.library.common.LoadState
import com.frewen.github.library.network.ResultCallBack

/**
 * @filename: BaseListViewModel
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/4/11 23:13
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
abstract class BaseListViewModel(private val application: Application) : ViewModel(), ResultCallBack<ArrayList<Any>> {
    // 定义易变性的LiveData
    val dataList = MutableLiveData<ArrayList<Any>>()

    val loading = MutableLiveData<LoadState>()

    val needMore = MutableLiveData<Boolean>()

    var lastPage: Int = -1

    var page = 1

    init {
        needMore.value = true
        loading.value = LoadState.NONE
        dataList.value = arrayListOf()
    }

    override fun onSuccess(result: ArrayList<Any>?) {

    }

}