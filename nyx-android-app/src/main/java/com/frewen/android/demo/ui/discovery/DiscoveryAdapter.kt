package com.frewen.android.demo.ui.discovery

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.frewen.android.demo.adapter.RecyclerViewHolderHelper
import com.frewen.android.demo.model.Discovery

/**
 * @filename: DiscoveryAdapter
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/7/24 07:30
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class DiscoveryAdapter(val fragment: DiscoveryFragment, val dataList: List<Discovery.Item>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    /**
     * 创建ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecyclerViewHolderHelper.getViewHolder(parent, viewType)

    override fun getItemViewType(position: Int) = RecyclerViewHolderHelper.getItemViewType(dataList[position])

    /**
     * ItemCount返回的是dataList的大小
     */
    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]

        when (holder) {

        }
    }
}
