package com.frewen.android.demo.adapter.holder

import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.frewen.android.demo.R
import com.frewen.android.demo.NyxApp
import com.frewen.android.demo.logic.model.BannerModel
import com.zhpan.bannerview.BaseViewHolder

/**
 * @filename: HomeBannerViewHolder
 * @introduction: 首页的View的顶部BannerView的数据
 * @author: Frewen.Wong
 * @time: 3/4/21 11:17 PM
 * Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
class HomeBannerViewHolder(view: View) : BaseViewHolder<BannerModel>(view) {

    override fun bindData(data: BannerModel?, position: Int, pageSize: Int) {
        val img = itemView.findViewById<ImageView>(R.id.img_banner_view)
        Log.d(
            "TAG",
            "bindData() called with: data = $data, position = $position, pageSize = $pageSize"
        )
        data?.let {
            Glide.with(
                NyxApp.getInstance(
                    NyxApp::class.java))
                    .load(it.imagePath)
                    .transition(DrawableTransitionOptions.withCrossFade(500))
                    .into(img)
        }
    }
}