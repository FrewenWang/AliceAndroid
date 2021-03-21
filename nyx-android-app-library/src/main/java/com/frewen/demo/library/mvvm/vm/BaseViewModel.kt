package com.frewen.demo.library.mvvm.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.frewen.aura.framework.mvvm.vm.AbsViewModel
import com.frewen.demo.library.network.ResultState
import com.frewen.demo.library.network.paresException
import com.frewen.demo.library.network.paresResult
import com.frewen.network.response.AuraNetResponse
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * @filename: BaseListViewModel
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/4/11 23:13
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
abstract class BaseViewModel() : AbsViewModel() {
    
    private val eventString: MutableLiveData<String> = MutableLiveData<String>()
}