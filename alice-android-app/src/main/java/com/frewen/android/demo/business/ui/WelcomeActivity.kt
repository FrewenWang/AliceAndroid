package com.frewen.android.demo.business.ui

import android.content.Intent
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.frewen.android.demo.R
import com.frewen.android.demo.business.adapter.WelcomeBannerAdapter
import com.frewen.android.demo.business.ui.main.MainActivity
import com.frewen.android.demo.databinding.ActivityWelcomeBinding
import com.frewen.android.demo.mvvm.viewmodel.WelcomeViewModel
import com.frewen.android.demo.utils.AppThemeUtil
import com.frewen.android.demo.utils.MmkvUtil
import com.frewen.android.demo.widgets.banner.WelcomeBannerViewHolder
import com.frewen.aura.framework.mvvm.activity.BaseVMDataBindingActivity
import com.frewen.demo.library.ktx.ext.gone
import com.frewen.demo.library.ktx.ext.visible
import com.zhpan.bannerview.BannerViewPager
import kotlinx.android.synthetic.main.activity_welcome.*

/**
 * @filename: WelcomeActivity
 * @author: Frewen.Wong
 * @time: 2021/6/14 09:55
 * @version: 1.0.0
 * @introduction:  Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
class WelcomeActivity : BaseVMDataBindingActivity<WelcomeViewModel, ActivityWelcomeBinding>() {

    private var resList = arrayOf("唱", "跳", "rap")

    private lateinit var mViewPager: BannerViewPager<String, WelcomeBannerViewHolder>

    override fun getContentViewId() = R.layout.activity_welcome

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        //防止出现按Home键回到桌面时，再次点击重新进入该界面bug
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT !== 0) {
            finish()
            return
        }

        mDataBinding.click = ProxyClick()
        welcome_baseview.setBackgroundColor(AppThemeUtil.getThemeColor(this))
        mViewPager = findViewById(R.id.banner_view)
        //是第一次打开App 显示引导页
        if (MmkvUtil.isFirstLaunch()) {
            //是第一次打开App 显示引导页
            welcome_image.gone()
            mViewPager.apply {
                adapter = WelcomeBannerAdapter()
                setLifecycleRegistry(lifecycle)
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        if (position == resList.size - 1) {
                            welcomeJoin.visible()
                        } else {
                            welcomeJoin.gone()
                        }
                    }
                })
                create(resList.toList())
            }
        } else {
            //不是第一次打开App 0.3秒后自动跳转到主页
            welcome_image.visible()
            mViewPager.postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                //带点渐变动画
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }, 300)
        }
    }


    inner class ProxyClick {
        fun toHomeActivity() {
            // CacheUtil.setFirst(false)
            startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
            finish()
            //带点渐变动画
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}