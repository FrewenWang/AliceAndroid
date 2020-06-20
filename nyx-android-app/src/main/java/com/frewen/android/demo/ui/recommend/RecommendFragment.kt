package com.frewen.android.demo.ui.recommend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.frewen.android.aura.annotations.FragmentDestination
import com.frewen.android.demo.R
import com.frewen.aura.framework.fragment.BaseFragment

@FragmentDestination(pageUrl = "main/tabs/recommend", asStarter = false)
class RecommendFragment : BaseFragment() {

    private lateinit var recommendViewModel: RecommendViewModel

    /**
     * Fragment子类需要实现的方法。用来生成Fragment需要的View
     */
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, b: Boolean): View {
        recommendViewModel =
                ViewModelProviders.of(this).get(RecommendViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_main_recommend, container, false)
        val textView: TextView = root.findViewById(R.id.text_recommend)
        recommendViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

}
