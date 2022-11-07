package com.frewen.nyx.hilt.demo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * 文章参考：https://developer.android.com/training/dependency-injection
 * 首先，第一步：使用@HiltAndroidApp
 *
 * 所有使用Hilt的应用都必须包含一个带有 @HiltAndroidApp 注释的 Application 类。
 *
 * Caused by: java.lang.IllegalStateException:
 *  Hilt Activity must be attached to an @AndroidEntryPoint Application.
 *  Found: class com.frewen.nyx.hilt.demo.NyxSunflowerApp
 *
 *  否则，会报上面的错误。
 *
 *  提示我么hilt Activity必现绑定一个一个HiltAndroidApp逐渐的Application上
 *
 *  @HiltAndroidApp将会触发Hilt的代码生成，作为程序依赖项容器的基类，生成的 Hilt 依附于 Application 的生命周期，
 *  他是 App 的父组件，提供访问其他组件的依赖。在 Application 中配置好后，就可以使用 Hilt 提供的组件了；
 *  组件包含 Application，Activity，Fragment，View，Service 等。
 */
@HiltAndroidApp
class NyxSunflowerApp : Application() {
    
    lateinit var roomServiceProvider: RoomServiceProvider
    
    override fun onCreate() {
        super.onCreate()
        // 实例化ROOM的提供类
        roomServiceProvider = RoomServiceProvider(this)
    }
    
}