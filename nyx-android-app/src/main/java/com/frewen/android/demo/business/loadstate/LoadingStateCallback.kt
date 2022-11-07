package com.frewen.android.demo.business.loadstate

import android.content.Context
import android.view.View
import com.frewen.demo.library.R
import com.kingja.loadsir.callback.Callback

/**
 * @filename: LoadingStateCallback
 * @author: Frewen.Wong
 * @time: 2021/6/24 08:30
 * @version: 1.0.0
 * @introduction:  Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
class LoadingStateCallback : Callback() {
    override fun onCreateView() = R.layout.layout_load_state_loading_view
    
    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }
}