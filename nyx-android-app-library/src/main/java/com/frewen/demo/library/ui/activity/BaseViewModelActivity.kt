package com.frewen.demo.library.ui.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.frewen.aura.framework.ui.BaseActivity
import com.frewen.demo.library.ui.holder.AuraDataBindingComponent
import com.frewen.demo.library.viewmodel.BaseViewModel

/**
 * @filename: BaseViewModelActivity
 * @introduction:
 *      使用ViewModel的基类。此类不包括DataBinding的相关逻辑。只会注入ViewModel
 * @author: Frewen.Wong
 * @time: 2020/7/23 07:54
 * @version: 1.0.0
 *      完成基础功能设计
 * @version: 1.0.1
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
abstract class BaseViewModelActivity<VM : BaseViewModel> : BaseActivity() {

    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentViewId())
        mViewModel = createViewModel()
    }

    private fun createViewModel(): VM {
        return ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory(application)
        ).get(getViewModelClass())
    }

    /**
     *
     */
    abstract fun getContentViewId(): Int

    /**
     * 返回ViewModel的Class文件
     */
    abstract fun getViewModelClass(): Class<VM>

}