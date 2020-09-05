package com.frewen.android.demo.ui.home

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.frewen.android.aura.annotations.FragmentDestination
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainHomeBinding
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment

@FragmentDestination(pageUrl = "main/tabs/home", asStarter = true)
class HomeFragment : BaseDataBindingFragment<FragmentMainHomeBinding>() {

    private lateinit var homeViewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 实例化ViewModel的对象
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_main_home;
    }
}
