package com.frewen.android.demo.business.samples.course.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.frewen.android.demo.R
import com.frewen.aura.framework.fragment.BaseViewFragment

/**
 * @filename: HomeFragment
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/3 18:57
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class RecommendViewFragment : BaseViewFragment() {
    
    private lateinit var recommendViewModel: RecommendViewModel
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recommendViewModel = ViewModelProviders.of(this).get(RecommendViewModel::class.java)
        
        val textureView: TextView = view.findViewById(R.id.content)
        
        recommendViewModel.text.observe(viewLifecycleOwner) {
            textureView.text = it
        }
        
    }
    
    override fun getLayoutId(): Int {
        return R.layout.fragment_tiktok_msg
    }
    
    
}