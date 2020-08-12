package com.frewen.nyx.hilt.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.frewen.nyx.hilt.demo.navigation.DemoNavigator
import com.frewen.nyx.hilt.demo.navigation.Screens
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 * 依赖项注入 (DI) 是一种广泛用于编程的技术，非常适用于 Android 开发。遵循 DI 的原则可以为良好的应用架构奠定基础。
 * 实现依赖项注入可为您带来以下优势：
 *       重用代码
 *       易于重构
 *       易于测试
 *
 *  @AndroidEntryPoint 会为项目中的每个Android 类生成一个单独的Hilt组件。
 *  这些组件可以从它们各自的父类接收依赖项，如组件层次结构中所述。
 */
class HiltDemoActivity : AppCompatActivity() {

    private lateinit var navigator: DemoNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hilt_demo)


        navigator = (applicationContext as NyxHiltApp).roomServiceProvider.provideNavigator(this)

        if (savedInstanceState == null) {
            navigator.navigateTo(R.id.main_container, Screens.BUTTONS)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        /// 判断Fragment的回退栈的数量
        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }
}