package com.frewen.android.demo.business.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.frewen.android.demo.R
import kotlinx.android.synthetic.main.fragment_camera2.*

/**
 * @filename: CameraFragment
 * @author: Frewen.Wong
 * @time: 12/27/20 2:10 PM
 * @version: 1.0.0
 * @introduction:  Class File Init
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class Camera2Fragment : Fragment() {
    /**
     * 设置Fragment的Layout布局的View
     */
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_camera2, container, false)
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // https://developer.android.com/reference/android/view/View.OnApplyWindowInsetsListener
//        capture_button.setOnApplyWindowInsetsListener {
//
//        }
    }
    
}