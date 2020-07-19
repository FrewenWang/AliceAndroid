package com.frewen.android.demo.ui.discovery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.frewen.android.aura.annotations.FragmentDestination
import com.frewen.android.demo.R
import com.frewen.aura.framework.fragment.BaseFragment
import com.google.android.material.tabs.TabLayout

@FragmentDestination(pageUrl = "main/tabs/discovery", asStarter = false)
class DiscoveryFragment : BaseFragment() {

    private val tabLayout: TabLayout? = null



    /**
     * Fragment子类需要实现的方法。用来生成Fragment需要的View
     */
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, b: Boolean): View {

        val root = inflater.inflate(R.layout.fragment_main_discovery, container, false)

        return root
    }

}
