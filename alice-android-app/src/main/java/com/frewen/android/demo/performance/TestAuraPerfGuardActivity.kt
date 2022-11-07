package com.frewen.android.demo.performance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.frewen.android.demo.R
import com.frewen.android.demo.performance.fragment.InstallFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_aura_perf_guard.*
import java.util.*

class TestAuraPerfGuardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aura_perf_guard)
        
        
        initView()
        
    }
    
    private fun initView() {
        initTabLayout()
        initViewPager()
    }
    
    private fun initViewPager() {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .add(R.id.fl_page_content, InstallFragment(), InstallFragment.javaClass.name)
            .add(R.id.fl_page_content, InstallFragment(), InstallFragment.javaClass.name)
            .add(R.id.fl_page_content, InstallFragment(), InstallFragment.javaClass.name)
        showFragment(0)
        
    }
    
    private fun initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("初始化"))
        tabLayout.addTab(tabLayout.newTab().setText("使用"))
        tabLayout.addTab(tabLayout.newTab().setText("测试工具"))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                showFragment(tab.position)
            }
            
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
    
    private fun showFragment(index: Int) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragments = fragmentManager.fragments
        for (i in fragments.indices) {
            fragmentTransaction.hide(fragments[i])
        }
        var fragmentName: String? = ""
        if (index == 0) {
            fragmentName = InstallFragment::class.java.simpleName
        } else if (index == 1) {
            fragmentName = InstallFragment::class.java.simpleName
        } else if (index == 2) {
            fragmentName = InstallFragment::class.java.simpleName
        }
        fragmentTransaction.show(
            Objects.requireNonNull(
                fragmentManager.findFragmentByTag(
                    fragmentName
                )
            )!!
        ).commit()
    }
    
}