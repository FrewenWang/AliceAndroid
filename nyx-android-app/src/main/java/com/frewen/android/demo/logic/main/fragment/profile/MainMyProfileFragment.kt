package com.frewen.android.demo.logic.main.fragment.profile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainMyProfileBinding
import com.frewen.android.demo.ktx.ext.init
import com.frewen.android.demo.ktx.ext.parseState
import com.frewen.android.demo.logic.model.IntegralModel
import com.frewen.aura.toolkits.utils.ToastUtils
import com.frewen.demo.library.ktx.ext.jumpByLogin
import com.frewen.demo.library.ktx.ext.nav
import com.frewen.demo.library.ktx.ext.navigateAction
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment
import kotlinx.android.synthetic.main.fragment_main_my_profile.*

/**
 * @filename: MainHomeFragment
 * @author: Frewen.Wong
 * @time: 2/6/21 5:21 PM
 * @version: 1.0.0
 * @introduction:  Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
class MainMyProfileFragment :
    BaseDataBindingFragment<MainMyProfileViewModel, FragmentMainMyProfileBinding>() {


    companion object {
        /**
         * 如果想要让Java代码也能调用这个伴生对象的方法
         * 需要加上@JvmStatic
         */
        @JvmStatic
        fun newInstance() = MainMyProfileFragment()
    }

    private var rank: IntegralModel? = null

    override fun getLayoutId() = R.layout.fragment_main_my_profile

    override fun initView(view: View, savedInstanceState: Bundle?) {
        binding?.myProfileVM = viewModel
        binding?.clickData = ProxyClick()
    }

    override fun initData(savedInstanceState: Bundle?) {
        swipeRefreshLayout.init {
            viewModel.getIntegral()
        }
    }

    override fun initObserver(savedInstanceState: Bundle?) {
        viewModel.integralData.observe(viewLifecycleOwner, Observer { resultState ->
            swipeRefreshLayout.isRefreshing = false
            parseState(resultState, {
                rank = it
                viewModel.rankInfo.set("id：${it.userId}　排名：${it.rank}")
                viewModel.integral.set(it.coinCount)
            }, {
                ToastUtils.showShort(it.errorMsg)
            })
        })
    }

    inner class ProxyClick {

        /** 登录 */
        fun login() {
            nav().jumpByLogin {}
        }

        /** 积分 */
        fun jumpIntegral() {
            nav().jumpByLogin {
                it.navigateAction(R.id.action_mainfragment_to_integralFragment,
                    Bundle().apply {
                        putParcelable("rank", rank)
                    }
                )
            }
        }
    }

}