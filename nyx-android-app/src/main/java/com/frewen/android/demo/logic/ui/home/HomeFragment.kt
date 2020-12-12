package com.frewen.android.demo.logic.ui.home

import android.os.Bundle
import android.view.View
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import com.frewen.android.aura.annotations.FragmentDestination
import com.frewen.android.demo.adapter.PostAdapter
import com.frewen.android.demo.logic.model.Post
import com.frewen.android.demo.logic.samples.tiktok.fragments.HomeViewModel
import com.frewen.demo.library.di.injector.Injectable
import com.frewen.demo.library.ui.fragment.BaseRecyclerViewDataBindingFragment
import com.scwang.smart.refresh.layout.api.RefreshLayout


@FragmentDestination(pageUrl = "main/tabs/home", asStarter = true)
class HomeFragment() : BaseRecyclerViewDataBindingFragment<Post, HomeViewModel>(), Injectable {

    private var feedType: String? = null

    override fun getViewModelClass(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        TODO("Not yet implemented")
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        TODO("Not yet implemented")
    }

    /**
     * 获取PagedList的Adapter
     */
    override fun getAdapter(): PagedListAdapter<*, *> {
        /**
         * 获取传入的Fragment的参数arguments
         */
        feedType = if (arguments == null) "all" else requireArguments().getString("feedType")
        return PostAdapter(context, feedType)
    }
}
