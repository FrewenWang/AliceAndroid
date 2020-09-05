package com.frewen.demo.library.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.frewen.aura.framework.fragment.BaseFragment
import com.frewen.demo.library.R
import kotlinx.android.synthetic.main.fragment_view_pager2.*

/**
 * @filename: BaseViewPager2Fragment
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/7/19 21:29
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
abstract class BaseViewPager2Fragment : BaseFragment() {

    companion object {
        private const val TAG = "BaseViewPager2Fragment"
    }

    /**
     * ViewFragment中缓存的页面的个数
     */
    private var offscreenPageLimit = 1

    abstract val createFragments: Array<Fragment>

    protected val adapter: ViewPager2Adapter by lazy { ViewPager2Adapter(requireActivity()).apply { addFragments(createFragments) } }

    override fun getLayoutId(): Int {
        return R.layout.fragment_view_pager2
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        initViewPager()
    }

    private fun initViewPager() {
        viewPager?.offscreenPageLimit = offscreenPageLimit
        viewPager?.adapter = adapter
    }

    /**
     * BaseViewPager2Fragment的内部类
     * ViewPager2Adapter   inner 修饰符进行修饰
     */
    class ViewPager2Adapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        /**
         * 可变的Fragment数组List
         */
        private val fragments = mutableListOf<Fragment>()

        fun addFragments(fragment: Array<Fragment>) {
            fragments.addAll(fragment)
        }

        override fun getItemCount() = fragments.size

        override fun createFragment(position: Int) = fragments[position]
    }
}