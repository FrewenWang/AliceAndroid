package com.frewen.demo.library.ui.holder

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingComponent
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder

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
        @JvmStatic
        fun editTextKeyListener(view: EditText?, listener: View.OnKeyListener) {
            view?.apply {
                this.setOnKeyListener(listener)
            }
        }

        @BindingAdapter("imgUrl")
        @JvmStatic
        fun setImageUrl(view: ImageView?, imageUrl: String) {
            view?.apply {
                var builder: RequestBuilder<Drawable> = Glide.with(view).load(imageUrl)
                // 这个地方是进行Glide加载图片的尺寸的复写，防止URL给的图片尺寸太大，占用内存，所以我们进行裁剪
                var layoutParams = view.layoutParams;
                if (layoutParams != null && layoutParams.height > 0 && layoutParams.width > 0) {
                    builder.override(layoutParams.width, layoutParams.height)
                }
                builder.into(view)
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