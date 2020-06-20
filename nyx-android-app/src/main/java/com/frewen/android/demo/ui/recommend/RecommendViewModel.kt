package com.frewen.android.demo.ui.recommend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecommendViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "推荐"
    }
    val text: LiveData<String> = _text
}