package com.frewen.android.demo.ui.discovery

import androidx.fragment.app.Fragment
import com.flyco.tablayout.listener.CustomTabEntity
import com.frewen.android.aura.annotations.FragmentDestination
import com.frewen.android.demo.ui.profile.MyProfileFragment
import com.frewen.github.library.ui.fragment.BaseViewPager2Fragment
import com.frewen.github.library.widgets.CustomTabEntityImpl

@FragmentDestination(pageUrl = "main/tabs/discovery", asStarter = false)
class DiscoveryFragment : BaseViewPager2Fragment() {
    /**
     * DiscoveryFragment 类中的伴生对象
     */
    companion object {
        fun newInstance() = DiscoveryFragment()
    }

    override val createTitles: ArrayList<CustomTabEntity>
        get() = ArrayList<CustomTabEntity>().apply {
            add(CustomTabEntityImpl("Tab1"))
            add(CustomTabEntityImpl("Tab2"))
            add(CustomTabEntityImpl("Tab3"))
        }
    override val createFragments: Array<Fragment>
        get() = arrayOf(MyProfileFragment(), MyProfileFragment(), MyProfileFragment())


}
