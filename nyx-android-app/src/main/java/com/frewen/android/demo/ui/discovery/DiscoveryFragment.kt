package com.frewen.android.demo.ui.discovery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.frewen.android.aura.annotations.FragmentDestination
import com.frewen.android.demo.R
import com.frewen.aura.framework.fragment.BaseFragment

@FragmentDestination(pageUrl = "main/tabs/discovery", asStarter = false)
class DiscoveryFragment : BaseFragment() {

    private lateinit var notificationsViewModel: DiscoveryViewModel

    /**
     * Fragment子类需要实现的方法。用来生成Fragment需要的View
     */
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, b: Boolean): View {
        notificationsViewModel =
                ViewModelProviders.of(this).get(DiscoveryViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_main_discovery, container, false)
        val textView: TextView = root.findViewById(R.id.text_discovery)
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

}
