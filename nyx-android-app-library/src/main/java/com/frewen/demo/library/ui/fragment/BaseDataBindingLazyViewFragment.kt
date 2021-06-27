package com.frewen.demo.library.ui.fragment

import android.os.Handler
import android.os.Looper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel

/**
 * @filename: BaseDataBindingFragment
 * @introduction: 带有DataBinding功能的Fragment基类
 * @author: Frewen.Wong
 * @time: 2020/7/23 07:54
 * @version: 1.0.0
 *      完成基础功能设计的DataBinding的逻辑
 *      主要是是调用DataBindingUtil.inflate的方法来进行布局控件绑定
 * @version: 1.0.1
 *      增加懒加载的View功能的Fragment
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
abstract class BaseDataBindingLazyViewFragment<VM : ViewModel, VDB : ViewDataBinding> :
    BaseDataBindingFragment<VM, VDB>() {
    /**
     * 判断Fragment是否是第一次加载
     */
    private var isFirstLoad: Boolean = true
    
    private val handler = Handler(Looper.getMainLooper())
    
    
    override fun onResume() {
        super.onResume()
        onLazyFragmentVisible()
    }
    
    /**
     * 当Fragment懒加载并且可见时
     */
    private fun onLazyFragmentVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirstLoad) {
            // 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿
            handler.postDelayed({
                if (isFirstLoad) {
                    lazyLoadData()
                }
                isFirstLoad = false
            }, 300)
        }
    }
    
    /**
     * Fragment的懒加载数据
     */
    abstract fun lazyLoadData()
    
}