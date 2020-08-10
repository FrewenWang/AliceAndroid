package com.frewen.android.demo.samples.dagger2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.frewen.android.demo.R
import javax.inject.Inject

/**
 *
 * 依赖项注入 (DI) 是一种广泛用于编程的技术，非常适用于 Android 开发。遵循 DI 的原则可以为良好的应用架构奠定基础。
 * 实现依赖项注入可为您带来以下优势：
 *       重用代码
 *      易于重构
 *      易于测试
 */
class HiltDemoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hilt_demo)
    }
}