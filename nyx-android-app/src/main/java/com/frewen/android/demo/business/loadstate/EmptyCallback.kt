package com.frewen.android.demo.business.loadstate


import com.frewen.android.demo.R
import com.kingja.loadsir.callback.Callback


class EmptyCallback : Callback() {
    
    override fun onCreateView(): Int {
        return R.layout.layout_load_state_empty_view
    }
    
}