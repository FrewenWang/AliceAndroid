package com.frewen.android.demo.ui.loadstate

import com.frewen.android.demo.R
import com.kingja.loadsir.callback.Callback


class ErrorCallback : Callback() {
    
    override fun onCreateView(): Int {
        return R.layout.layout_load_state_error_view
    }
    
}