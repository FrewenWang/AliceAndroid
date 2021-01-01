package com.frewen.android.demo.logic.samples.media.camera2.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceView
import kotlin.math.roundToInt

/**
 * @filename: AutoFitSurfaceView
 * @author: Frewen.Wong
 * @time: 12/27/20 2:25 PM
 * @version: 1.0.0
 * @introduction:  使用@JvmOverloads可以将我们View所有的构造函数的相关参数都直接传入
 * 这个控件的封装我们可以看：https://www.jianshu.com/p/d6383f5dcf5b
 *                        https://github.com/Ashok-Varma/BottomNavigation
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class AutoFitSurfaceView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : SurfaceView(context, attrs, defStyle) {
    
    private var aspectRatio = 0f
    
    /**
     * 设置此视图的纵横比。
     * 视图的大小将根据从参数计算出的比率进行测量。
     * @param width  相机分辨率水平尺寸
     * @param height 相机分辨率垂直尺寸
     */
    fun setAspectRatio(width: Int, height: Int) {
        require(width > 0 && height > 0) { "Size cannot be negative" }
        aspectRatio = width.toFloat() / height.toFloat()
        holder.setFixedSize(width, height)
        requestLayout()
    }
    
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 获取当前View测量之后的宽度和高度
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getMode(heightMeasureSpec)
        
        if (aspectRatio == 0f) {
            setMeasuredDimension(width, height)
        } else {
            // 先暂时声明新的高度和宽度
            val newWidth: Int
            val newHeight: Int
            // 这个地方，我们可以计算一下。如果是宽度>高度
            val actualRatio = if (width > height) aspectRatio else 1f / aspectRatio
            if (width < height * actualRatio) {
                newHeight = height
                newWidth = (height * actualRatio).roundToInt()
            } else {
                newWidth = width
                newHeight = (width / actualRatio).roundToInt()
            }
            
            Log.d(TAG, "Measured dimensions set: $newWidth x $newHeight")
            setMeasuredDimension(newWidth, newHeight)
        }
    }
    
    companion object {
        private val TAG = AutoFitSurfaceView::class.java.simpleName
    }
    
    
}