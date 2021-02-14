package com.frewen.demo.library.network

import androidx.lifecycle.MutableLiveData
import com.frewen.network.response.AuraNetResponse
import com.frewen.network.response.exception.AuraNetException

/**
 * @filename: ResultState
 * @author: Frewen.Wong
 * @time: 2/13/21 8:33 AM
 * @version: 1.0.0
 * @introduction:  Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
sealed class ResultState<out T> {
    companion object {
        fun <T> onSuccess(data: T): ResultState<T> = Success(data)
        fun <T> onLoading(loadingMessage: String): ResultState<T> = Loading(loadingMessage)
        fun <T> onError(error: AuraNetException): ResultState<T> = Error(error)
        
        
        data class Loading(val loadingMessage: String) : ResultState<Nothing>()
        data class Success<out T>(val data: T) : ResultState<T>()
        data class Error(val error: AuraNetException) : ResultState<Nothing>()
    }
}

/**
 * 处理返回值
 * @param result 请求结果
 */
fun <T> MutableLiveData<ResultState<T>>.paresResult(result: AuraNetResponse<T>) {
    value = when {
        result.isSuccess -> {
            ResultState.onSuccess(result.data)
        }
        else -> {
            ResultState.onError(AuraNetException(result.code, result.msg))
        }
    }
}

/**
 * 异常转换异常处理
 */
fun <T> MutableLiveData<ResultState<T>>.paresException(e: Throwable) {
    this.value = ResultState.onError(AuraNetException.handleException(e))
}
