package com.frewen.android.demo.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.frewen.android.aura.annotations.FragmentDestination
import com.frewen.android.demo.databinding.FragmentMainMyProfileBinding
import com.frewen.aura.framework.fragment.BaseFragment

@FragmentDestination(pageUrl = "main/tabs/myProfile", asStarter = false)
class MyProfileFragment : BaseFragment() {

    private lateinit var myProfileViewModel: MyProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 进行ViewModel的实例化的相关逻辑。其实里面就是一个工厂方法
        myProfileViewModel =
                ViewModelProviders.of(this).get(MyProfileViewModel::class.java)
    }

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, b: Boolean): View {
        // 我们来使用FragmentProfileBinding 来代替之前的inflater.inflate布局的方式
        // 这个时候，我们返回的值也需要变化成binding.root
        val binding = FragmentMainMyProfileBinding.inflate(inflater, container, false)
        // val root = inflater.inflate(R.layout.fragment_main_my_profile, container, false)

        // 这个通过给FragmentProfileBinding的对象进行赋值
        binding.myProfileViewModel = myProfileViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}
