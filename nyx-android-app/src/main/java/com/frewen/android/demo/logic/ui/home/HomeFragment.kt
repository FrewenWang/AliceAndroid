package com.frewen.android.demo.logic.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import com.frewen.android.aura.annotations.FragmentDestination
import com.frewen.android.demo.adapter.PostAdapter
import com.frewen.android.demo.logic.model.Post
import com.frewen.android.demo.logic.samples.tiktok.fragments.HomeViewModel
import com.frewen.android.demo.player.exo.PageListPlayerDetector
import com.frewen.demo.library.di.injector.Injectable
import com.frewen.demo.library.ui.fragment.BaseRecyclerViewDataBindingFragment
import com.scwang.smart.refresh.layout.api.RefreshLayout


@FragmentDestination(pageUrl = "main/tabs/home", asStarter = true)
class HomeFragment() : BaseRecyclerViewDataBindingFragment<Post, HomeViewModel>(), Injectable {
    /**
     * 顾名思义，这是指一个延迟初始化的变量。在kotlin里面，如果在类型声明之后没有使用符号?，则表示该变量不会为null。
     * 但是这个时候会要求我们初始化一个值。
     * 有些时候，我们在声明变量的时候，并不能初始化这个变量。
     * 一个声明成lateinit的变量，如果在整个代码里面都没有进行任何的初始化，
     * 那么能否编译通过？如果你加上了lateinit关键字，kotlin的编译器不会做这种检查。
     * 如果你将变量声明为lateinit，它就认为你肯定会初始化，至于你是怎么初始化它的，它就不管了
     * 1. lateinit 延迟加载
     * 2.lateinit 只能修饰, 非kotlin基本类型
     * 3如果你的代码真的显示初始化了lateinit变量，而又抛出了UninitializedPropertyAccessException异常， 因为你恰好将变量初始化为null了
     * 因为Kotlin会使用null来对每一个用lateinit修饰的属性做初始化，而基础类型是没有null类型，所以无法使用lateinit。
     */
    private lateinit var playDetector: PageListPlayerDetector
    private var feedType: String? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        playDetector = PageListPlayerDetector(this, mRecyclerView)
        viewModel.setFeedType(feedType)

        // 这个地方直接传入this.在Kotlin里面是不行的
        // 后面是我们实例化的Observer对象
        viewModel.getCacheLiveData().observe(viewLifecycleOwner, Observer<PagedList<Post>> { posts ->
            submitList(posts)
        })
    }


    override fun getViewModelClass(): Class<HomeViewModel> {
        return HomeViewModel::class.java
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
