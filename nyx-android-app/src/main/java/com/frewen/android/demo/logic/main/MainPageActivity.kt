package com.frewen.android.demo.logic.main

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.ActivityMainPageBinding
import com.frewen.android.demo.mvvm.viewmodel.MainPageViewModel
import com.frewen.aura.toolkits.utils.ToastUtils
import com.frewen.demo.library.ui.activity.BaseDataBindingActivity

/**
 * App的主页的Activity的实现
 */
class MainPageActivity : BaseDataBindingActivity<MainPageViewModel, ActivityMainPageBinding>() {
    var exitTime = 0L
    override fun getContentViewId(): Int {
        return R.layout.activity_main_page
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        initView(savedInstanceState)
        
    }
    
    /**
     * 初始化View
     */
    private fun initView(savedInstanceState: Bundle?) {
        
        initBackPressListener()
    }
    
    /**
     * 监听按返回键的逻辑
     */
    private fun initBackPressListener() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 我们从这个宿主的HostFragment中获取导航控制器
                val navController = Navigation.findNavController(this@MainPageActivity, R.id.host_fragment)
                // 判断导航控制器中的navController中的currentDestination是不是mainFragment
                if (navController.currentDestination != null && navController.currentDestination!!.id != R.id.mainFragment) {
                    //如果当前界面不是主页，那么直接调用返回即可
                    navController.navigateUp()
                } else {
                    //是主页
                    if (System.currentTimeMillis() - exitTime > 2000) {
                        ToastUtils.showShort("再按一次退出程序")
                        exitTime = System.currentTimeMillis()
                    } else {
                        finish()
                    }
                }
            }
        })
    }
    
    
}