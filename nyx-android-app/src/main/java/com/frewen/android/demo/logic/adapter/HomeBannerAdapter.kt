package com.frewen.android.demo.logic.adapter

/**
 * 作者　: hegaojian
 * 时间　: 2020/2/20
 * 描述　:
 */

import android.view.View
import com.frewen.android.demo.R
import com.frewen.android.demo.adapter.holder.HomeBannerViewHolder
import com.frewen.android.demo.logic.model.BannerModel
import com.zhpan.bannerview.BaseBannerAdapter

class HomeBannerAdapter : BaseBannerAdapter<BannerModel, HomeBannerViewHolder>() {
    override fun getLayoutId(viewType: Int): Int {
        return R.layout.layout_include_bannner_view_common
    }

    override fun createViewHolder(itemView: View, viewType: Int): HomeBannerViewHolder {
        return HomeBannerViewHolder(itemView);
    }

    override fun onBind(
            holder: HomeBannerViewHolder?,
            data: BannerModel?,
            position: Int,
            pageSize: Int) {
        holder?.bindData(data, position, pageSize);
    }
}
