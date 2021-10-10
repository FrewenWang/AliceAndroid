package com.frewen.demo.library.ktx.ext

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.frewen.demo.library.mvvm.vm.BaseViewModel
import com.frewen.demo.library.network.ResultState
import com.frewen.demo.library.network.paresException
import com.frewen.demo.library.network.paresResult
import com.frewen.network.response.AuraNetResponse
import com.frewen.network.response.exception.AuraNetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * @filename: BaseViewModelExt
 * @author: Frewen.Wong
 * @time: 3/18/21 10:51 PM
 * @version: 1.0.0
 * @introduction:  Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */

/**
 * 过滤服务器结果，失败抛异常
 * @param block 请求体方法，必须要用suspend关键字修饰
 * @param success 成功回调
 * @param error 失败回调 可不传
 * @param isShowDialog 是否显示加载框
 * @param loadingMessage 加载框提示内容
 */
fun <T> BaseViewModel.request(
    block: suspend () -> AuraNetResponse<T>,
    success: (T) -> Unit,
    error: (AuraNetException) -> Unit = {},
    isShowDialog: Boolean = false,
    loadingMessage: String = "请求网络中..."
): Job {
    //如果需要弹窗 通知Activity/fragment弹窗
    return viewModelScope.launch {
        runCatching {
            //请求体
            block()
        }.onSuccess {
            runCatching {
                //校验请求结果码是否正确，不正确会抛出异常走下面的onFailure
                executeResponse(it) { t ->
                    success(t)
                }
            }.onFailure { e ->
                //打印错误消息
                println(it.msg)
                //失败回调
                error(AuraNetException.handleException(e))
            }
        }.onFailure {
            // 如果runCatching方法发生异常，就会调用这个方法
            //失败回调
            error(AuraNetException.handleException(it))
        }
    }
}


fun <T> BaseViewModel.request(
    block: suspend () -> AuraNetResponse<T>,
    resultState: MutableLiveData<ResultState<T>>,
    isShowDialog: Boolean = false,
    loadingMessage: String = "请求网络中..."
): Job {
    return viewModelScope.launch {
        runCatching {
            //请求体
            block()
        }.onSuccess {
            resultState.paresResult(it)
        }.onFailure {
            resultState.paresException(it)
        }
    }
}

/**
 * 请求结果过滤，判断请求服务器请求结果是否成功，不成功则会抛出异常
 */
suspend fun <T> executeResponse(
    response: AuraNetResponse<T>,
    success: suspend CoroutineScope.(T) -> Unit
) {
    coroutineScope {
        when {
            response.isSuccess -> {
                success(response.data)
            }
            else -> {
                throw AuraNetException(response.code, response.msg)
            }
        }
    }
}