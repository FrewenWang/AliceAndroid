package com.frewen.android.demo.ui.discovery

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.frewen.android.aura.annotations.FragmentDestination
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainHomeBinding
import com.frewen.github.library.di.injector.Injectable
import com.frewen.github.library.ui.fragment.BaseDataBindingFragment
import kotlinx.android.synthetic.main.fragment_main_discovery.*
import javax.inject.Inject

/**
 *   所有使用实现自Injectable的类。需要有注册inject的
 *   Process: com.frewen.android.demo.debug, PID: 2570
 *   java.lang.IllegalArgumentException: No injector was found for com.frewen.android.demo.ui.discovery.DiscoveryFragment
 */
@FragmentDestination(pageUrl = "main/tabs/discovery", asStarter = false)
class DiscoveryFragment : BaseDataBindingFragment<FragmentMainHomeBinding>(), Injectable {

    private lateinit var adapter: DiscoveryAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: DiscoveryViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(getViewModelClass())

        adapter = DiscoveryAdapter(this, viewModel.dataList)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        refreshLayout.setOnRefreshListener { viewModel.onRefresh() }
        refreshLayout.setOnLoadMoreListener { viewModel.onLoadMore() }

        initObserver()
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_main_discovery
    }




    /**
     * 传入DiscoveryViewModel的
     */
    private fun getViewModelClass(): Class<DiscoveryViewModel> = DiscoveryViewModel::class.java


    private fun initObserver() {

    }


}
