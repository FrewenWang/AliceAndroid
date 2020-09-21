package com.frewen.demo.library.mvvm.viewmodel

import java.lang.reflect.ParameterizedType

/**
 * @filename: ViewModelExt
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/20 21:29
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@Suppress("UNCHECKED_CAST")
fun <VM> getViewModelClass(obj: Any, position: Int): VM {
    // 我们来解释一下这段代码：
    /// 通过getGenericSuperclass方法可以获取当前对象的直接超类的Type，使用该方法可以获取到泛型T的具体类型
    // 然后我们获取实际类型参数的position类型就是我们的泛型类型对象
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[position] as VM
}