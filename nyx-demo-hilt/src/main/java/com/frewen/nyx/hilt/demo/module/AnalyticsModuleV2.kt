package com.frewen.nyx.hilt.demo.module

import com.frewen.nyx.hilt.demo.service.AnalyticsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

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
object AnalyticsModuleV2 {


    /**
     * 使用 @Provides 注入实例
     * 接口不是无法通过构造函数注入类型的唯一一种情况。
     * 如果我们依赖的实例是来自第三方依赖（如 Retrofit、OkHttpClient 或 Room 数据库等类），
     * 或者必须使用调用其构造函数来进行创建实例，也无法通过构造函数注入。
     *
     * 接着前面的例子来讲。如果 AnalyticsService 类不直接归您所有，
     * 您可以告知 Hilt 如何提供此类型的实例，方法是在Hilt模块内创建一个函数，
     * 并使用 @Provides 为该函数添加注解。
     *
     * 带有这个注解的函数会向 Hilt 提供以下信息：
     *
     *      函数返回类型会告知 Hilt 函数提供哪个类型的实例。
     *      函数参数会告知 Hilt 相应类型的依赖项。
     *      函数主体会告知 Hilt 如何提供相应类型的实例。每当需要提供该类型的实例时，Hilt 都会执行函数主体。
     */
    @Provides
    fun bindAnalyticsService(
    ): AnalyticsService {
        return Retrofit.Builder()
                .baseUrl("https://wwww.baidu.com")
                .build()
                .create(AnalyticsService::class.java)
    }

}