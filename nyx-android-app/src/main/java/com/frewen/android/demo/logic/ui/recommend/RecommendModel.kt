package com.frewen.android.demo.logic.ui.recommend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecommendModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "推荐"
    }
    val text: LiveData<String> = _text
}