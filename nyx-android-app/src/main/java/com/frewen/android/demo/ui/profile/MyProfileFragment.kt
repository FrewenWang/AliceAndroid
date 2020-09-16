package com.frewen.android.demo.ui.profile

import com.frewen.android.aura.annotations.FragmentDestination
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainMyProfileBinding
import com.frewen.demo.library.di.injector.Injectable
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment

@FragmentDestination(pageUrl = "main/tabs/myProfile", asStarter = false)
class MyProfileFragment : BaseDataBindingFragment<FragmentMainMyProfileBinding, MyProfileViewModel>(), Injectable {

    override fun getViewModelClass(): Class<MyProfileViewModel> {
        return MyProfileViewModel::class.java
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_main_my_profile
    }


}
