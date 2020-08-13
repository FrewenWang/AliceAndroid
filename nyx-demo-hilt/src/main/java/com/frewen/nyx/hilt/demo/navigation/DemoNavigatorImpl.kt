package com.frewen.nyx.hilt.demo.navigation

import androidx.fragment.app.FragmentActivity
import com.frewen.nyx.hilt.demo.fragment.FirstFragment
import com.frewen.nyx.hilt.demo.fragment.SecondFragment
import javax.inject.Inject

class DemoNavigatorImpl @Inject constructor(private val activity: FragmentActivity) : DemoNavigator {
    /**
     * 我们简单封装之后，在这里面调用Fragment的切换的逻辑
     */
    override fun navigateTo(containerId: Int, screen: Screens) {

        val fragment = when (screen) {
            Screens.BUTTONS -> FirstFragment()
            Screens.LOGS -> SecondFragment()

        }
        activity.supportFragmentManager
                .beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(fragment::class.java.canonicalName)
                .commit()
    }
}