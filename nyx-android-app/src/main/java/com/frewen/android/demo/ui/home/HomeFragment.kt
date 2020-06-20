package com.frewen.android.demo.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.frewen.android.aura.annotations.FragmentDestination
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainHomeBinding
import com.frewen.aura.framework.fragment.BaseFragment

@FragmentDestination(pageUrl = "main/tabs/home", asStarter = true)
class HomeFragment : BaseFragment() {
    /**
     * 只要在 {@link #R.layout.fragment_main_home}  里面根布局里面使用<layout>布局包含
     * 就会生成FragmentHomeBinding的对象
     */
    private var viewDataBinding: FragmentMainHomeBinding? = null
    private lateinit var homeViewModel: HomeViewModel
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, b: Boolean): View {
        // 实例化ViewModel的对象
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        // 实例化ViewDataBinding的
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_home, container, false)

//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return viewDataBinding!!.root
    }
}
