package com.frewen.android.demo.adapter

import android.view.View
import com.frewen.android.demo.R
import com.frewen.android.demo.widgets.banner.WelcomeBannerViewHolder
import com.zhpan.bannerview.BaseBannerAdapter

class WelcomeBannerAdapter : BaseBannerAdapter<String, WelcomeBannerViewHolder>() {
    
    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_banner_welcome
    }
    
    override fun createViewHolder(itemView: View, viewType: Int): WelcomeBannerViewHolder {
        return WelcomeBannerViewHolder(itemView);
    }
    
    override fun onBind(
        holder: WelcomeBannerViewHolder?,
        data: String?,
        position: Int,
        pageSize: Int
    ) {
        holder?.bindData(data, position, pageSize);
    }
}
