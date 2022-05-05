package com.frewen.android.demo.logic.adapter

import android.view.View
import com.frewen.android.demo.R
import com.frewen.android.demo.logic.adapter.holder.HomeBannerViewHolder
import com.frewen.android.demo.logic.model.BannerModel
import com.zhpan.bannerview.BaseBannerAdapter

class HomeBannerAdapter : BaseBannerAdapter<BannerModel, HomeBannerViewHolder>() {
    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_image_banner_view_home
    }

    override fun createViewHolder(itemView: View, viewType: Int): HomeBannerViewHolder {
        return HomeBannerViewHolder(itemView)
    }

    override fun onBind(
        holder: HomeBannerViewHolder?,
        data: BannerModel?,
        position: Int,
        pageSize: Int
    ) {
        holder?.bindData(data, position, pageSize);
    }
}
