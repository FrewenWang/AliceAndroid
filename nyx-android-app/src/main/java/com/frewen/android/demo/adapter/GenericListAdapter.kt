package com.frewen.android.demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


/** Type helper used for the callback triggered once our view has been bound */
typealias BindCallback<T> = (view: View, data: T, position: Int) -> Unit

/**
 * @filename: GenericListAdapter
 * @author: Frewen.Wong
 * @time: 12/27/20 1:59 PM
 * @version: 1.0.0
 * @introduction:  通用类型的列表适配器，旨在用于中小型数据列表
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class GenericListAdapter<T>(
        private val dataList: List<T>,
        private val itemLayoutId: Int? = null,
        private val itemViewFactory: (() -> View)? = null,
        private val onBind: BindCallback<T>
) : RecyclerView.Adapter<GenericListAdapter.GenericListViewHolder>() {
    class GenericListViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GenericListViewHolder(when {
        itemViewFactory != null -> itemViewFactory.invoke()
        itemLayoutId != null -> {
            LayoutInflater.from(parent.context)
                    .inflate(itemLayoutId, parent, false)
        }
        else -> {
            throw IllegalStateException(
                    "Either the layout ID or the view factory need to be non-null")
        }
    })
    
    override fun onBindViewHolder(holder: GenericListViewHolder, position: Int) {
        if (position < 0 || position > dataList.size) return
        onBind(holder.view, dataList[position], position)
    }
    
    override fun getItemCount() = dataList.size
}