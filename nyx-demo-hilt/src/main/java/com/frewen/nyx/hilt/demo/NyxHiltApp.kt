package com.frewen.nyx.hilt.demo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * 我们先来学习一下，什么是依赖注入：
 * 依赖项注入 (DI) 是一种广泛用于编程的技术，非常适用于 Android 开发。遵循 DI 的原则可以为良好的应用架构奠定基础。
 * 实现依赖项注入可为您带来以下优势：
 *       重用代码
 *       易于重构
 *       易于测试
 *
 */
/**
 * 首先，第一步：使用@HiltAndroidApp
 *
 * 所有使用 Hilt 的应用都必须包含一个带有 @HiltAndroidApp 注释的 Application 类。
 *
 * Caused by: java.lang.IllegalStateException:
 *  Hilt Activity must be attached to an @AndroidEntryPoint Application.
 *  Found: class com.frewen.nyx.hilt.demo.NyxHiltApp
 *
 *  否则，会报上面的错误。
 *
 *  提示我么hilt Activity必现绑定一个一个HiltAndroidApp逐渐的Application上
 *
 *  @HiltAndroidApp 会触发 Hilt 的代码生成操作，生成的代码包括应用的一个基类，该基类充当应用级依赖项容器。
 *  生成的这一 Hilt 组件会附加到 Application 对象的生命周期，并为其提供依赖项。
 *  此外，它也是应用的父组件，这意味着，其他组件可以访问它提供的依赖项。
 */
@HiltAndroidApp
class NyxHiltApp : Application() {

    lateinit var roomServiceProvider: RoomServiceProvider

    override fun onCreate() {
        super.onCreate()

        roomServiceProvider = RoomServiceProvider(this)
    }

}