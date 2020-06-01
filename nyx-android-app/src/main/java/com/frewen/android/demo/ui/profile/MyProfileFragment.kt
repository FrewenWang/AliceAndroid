package com.frewen.android.demo.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.frewen.android.demo.databinding.FragmentProfileBinding

class MyProfileFragment : Fragment() {

    private lateinit var myProfileViewModel: MyProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 进行ViewModel的实例化的相关逻辑。其实里面就是一个工厂方法
        myProfileViewModel =
                ViewModelProviders.of(this).get(MyProfileViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // 我们来使用FragmentProfileBinding 来代替之前的inflater.inflate布局的方式
        // 这个时候，我们返回的值也需要变化成binding.root
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        // val root = inflater.inflate(R.layout.fragment_profile, container, false)

        // 这个通过给FragmentProfileBinding的对象进行赋值
        binding.loginViewModel = myProfileViewModel

        return binding.root
    }
}
