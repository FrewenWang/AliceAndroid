package com.frewen.android.demo.ktx.ext

import com.frewen.demo.library.network.ResultState
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment
import com.frewen.network.response.exception.AuraNetException

/**
 * @filename: BaseDataBindingFragmentExt
 * @author: Frewen.Wong
 * @time: 2/14/21 10:33 AM
 * @version: 1.0.0
 * @introduction:  Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */

/**
 * 显示页面状态，这里有个技巧，成功回调在第一个，其后两个带默认值的回调可省
 * @param resultState 接口返回值
 * @param onLoading 加载中
 * @param onSuccess 成功回调
 * @param onError 失败回调
 *
 */
fun <T> BaseDataBindingFragment<*, *>.parseState(
        resultState: ResultState<T>,
        onSuccess: (T) -> Unit,
        onError: ((AuraNetException) -> Unit)? = null,
        onLoading: (() -> Unit)? = null
) {
    when (resultState) {
        is ResultState.Loading -> {
            showLoading(resultState.loadingMessage)
            onLoading?.invoke()
        }
        is ResultState.Success -> {
//            dismissLoading()
            onSuccess(resultState.data)
        }
        is ResultState.Error -> {
//            dismissLoading()
            onError?.run { this(resultState.error) }
        }
        else -> {
        
        }
    }
}