package com.frewen.android.demo.logic.ui.discovery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frewen.android.demo.logic.model.Discovery
import javax.inject.Inject

/**
 *
 */
class DiscoveryViewModel @Inject constructor() : ViewModel() {

    var dataList = ArrayList<Discovery.Item>()

    /**
     * 声明一个可变的MutableLiveData
     */
    private var requestParamLiveData = MutableLiveData<String>()


    private val _text = MutableLiveData<String>().apply {
        value = "发现"
    }


    val text: LiveData<String> = _text


    fun onRefresh() {
        requestParamLiveData.value = ""
    }

    fun onLoadMore() {
        requestParamLiveData.value = ""
    }
}