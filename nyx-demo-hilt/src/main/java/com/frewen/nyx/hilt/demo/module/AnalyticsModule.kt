package com.frewen.nyx.hilt.demo.module

import com.frewen.nyx.hilt.demo.service.AnalyticsService
import com.frewen.nyx.hilt.demo.service.impl.AnalyticsServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * Hilt 模块(Hilt Module)
 *
 * 有时，类型不能通过构造函数注入。发生这种情况可能有多种原因。
 * 例如:
 *  1、您不能通过构造函数注入接口。
 *  2、您也不能通过构造函数注入不归您所有的类型，如来自外部库的类。
 * 在这些情况下，您可以使用 Hilt 模块向 Hilt 提供绑定信息。
 *
 * Hilt 模块是一个带有 @Module 注释的类。与 Dagger 模块一样，它会告知 Hilt 如何提供某些类型的实例。
 * 与 Dagger 模块不同的是，您必须使用 @InstallIn 为 Hilt 模块添加注释，
 * 以告知 Hilt 每个模块将用在或安装在哪个 Android 类中。
 *
 * 您在 Hilt 模块中提供的依赖项可以在生成的所有与 Hilt 模块安装到的 Android 类关联的组件中使用。
 *
 * Hilt 模块 AnalyticsModule
 * 带有 @InstallIn(ActivityComponent::class) 注释，
 * 因为您希望 Hilt 将该依赖项注入 ExampleActivity。
 * 此注解意味着，AnalyticsModule 中的所有依赖项都可以在应用的所有 Activity 中使用。
 */
@Module
@InstallIn(ActivityComponent::class)
abstract class AnalyticsModule {


    /**
     * 使用 @Binds 注入接口实例
     * 以 AnalyticsService 为例。
     * 如果 AnalyticsService 是一个接口，则您无法通过构造函数注入它，而应向 Hilt 提供绑定信息，
     * 方法是在 Hilt 模块内创建一个带有 @Binds 注释的抽象函数。
     *
     * @Binds 注释会告知 Hilt 在需要提供接口的实例时要使用哪种实现。
     * 带有这个注解的函数会向 Hilt 提供以下信息：
     *
     * 函数返回类型会告知 Hilt 函数提供哪个接口的实例。
     * 函数参数会告知 Hilt 要提供哪种实现。
     */
    @Binds
    abstract fun bindAnalyticsService(
            analyticsServiceImpl: AnalyticsServiceImpl
    ): AnalyticsService

}