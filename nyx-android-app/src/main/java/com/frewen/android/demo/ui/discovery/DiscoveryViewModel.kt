package com.frewen.android.demo.ui.discovery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiscoveryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "发现"
    }
    val text: LiveData<String> = _text
}