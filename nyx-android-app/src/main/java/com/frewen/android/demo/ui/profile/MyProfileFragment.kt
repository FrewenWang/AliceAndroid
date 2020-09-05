package com.frewen.android.demo.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.frewen.android.aura.annotations.FragmentDestination
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainMyProfileBinding
import com.frewen.aura.framework.fragment.BaseFragment
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment

@FragmentDestination(pageUrl = "main/tabs/myProfile", asStarter = false)
class MyProfileFragment : BaseDataBindingFragment<FragmentMainMyProfileBinding>() {

    private lateinit var myProfileViewModel: MyProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 进行ViewModel的实例化的相关逻辑。其实里面就是一个工厂方法
        myProfileViewModel =
                ViewModelProviders.of(this).get(MyProfileViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_main_my_profile
    }
}
