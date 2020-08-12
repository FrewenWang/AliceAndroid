package com.frewen.nyx.hilt.demo

import android.content.Context
import com.frewen.nyx.hilt.demo.service.AnalyticsService
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

/**
 * 为了执行字段注入，Hilt 需要知道如何从相应组件提供必要依赖项的实例。
 * “绑定”包含将某个类型的实例作为依赖项提供所需的信息。
 * 向 Hilt 提供绑定信息的一种方法是构造函数注入。
 * 在某个类的构造函数中使用 @Inject 注释，以告知 Hilt 如何提供该类的实例：
 *
 * 在一个类的代码中，带有注释的构造函数的参数即是该类的依赖项。
 * 在本例中，AnalyticsService 是 AnalyticsAdapter 的一个依赖项。
 * 因此，Hilt 还必须知道如何提供 AnalyticsService 的实例。
 *
 *
 */
class AnalyticsAdapter @Inject constructor(
        @ActivityContext private val context: Context,
        private val service: AnalyticsService
) {

}