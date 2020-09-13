package com.frewen.android.demo.ui.discovery

import com.frewen.android.aura.annotations.FragmentDestination
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainDiscoveryBinding
import com.frewen.android.demo.ui.profile.MyProfileViewModel
import com.frewen.demo.library.di.injector.Injectable
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment

/**
 *   所有使用实现自Injectable的类。需要有注册inject的
 *   Process: com.frewen.android.demo.debug, PID: 2570
 *   java.lang.IllegalArgumentException: No injector was found for com.frewen.android.demo.ui.discovery.DiscoveryFragment
 */
@FragmentDestination(pageUrl = "main/tabs/discovery", asStarter = false)
class DiscoveryFragment : BaseDataBindingFragment<FragmentMainDiscoveryBinding, MyProfileViewModel>(), Injectable {

    override fun getViewModelClass(): Class<MyProfileViewModel> {
        return MyProfileViewModel::class.java
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_main_my_profile
    }


}
