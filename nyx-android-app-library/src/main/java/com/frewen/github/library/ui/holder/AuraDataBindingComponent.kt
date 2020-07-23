package com.frewen.github.library.ui.holder

import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingComponent

/**
 * @filename: AuraDataBindingComponent
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/7/23 07:59
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class DataBindingExpandUtils {

    companion object {
        /**
         * EditText 按键监听
         */
        @BindingAdapter("keyListener")
        fun editTextKeyListener(view: EditText?, listener: View.OnKeyListener) {
            view?.apply {
                this.setOnKeyListener(listener)
            }
        }
    }

}

/**
 * 这个实现DataBindingComponent组件接口
 * 这个我们需要传入的DataBindingExpandUtils.Companion
 * 编译工作会自动帮我们生成getCompanion方法
 */
class AuraDataBindingComponent : DataBindingComponent {
    override fun getCompanion(): DataBindingExpandUtils.Companion = DataBindingExpandUtils
}