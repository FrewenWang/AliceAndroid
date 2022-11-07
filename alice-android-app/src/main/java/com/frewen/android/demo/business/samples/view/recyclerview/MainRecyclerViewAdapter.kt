package com.frewen.android.demo.business.samples.view.recyclerview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.frewen.android.demo.R
import com.frewen.android.demo.business.samples.view.recyclerview.MainRecyclerViewAdapter.MainRecyclerViewHolder

/**
 * @filename: MainRecyclerViewAdapter
 * @introduction:
 *
 *  MutableList<T> 是可以进行写操作的 List，例如用于在特定位置添加或删除元素。
 * @author: Frewen.Wong
 * @time: 2020/7/29 23:09
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class MainRecyclerViewAdapter(private val context: Context, private val dataList: MutableList<String>) : RecyclerView.Adapter<MainRecyclerViewHolder>() {
    /**
     * 进行Inflater的ItemView的。然后将View传入MainRecyclerViewHolder构造函数
     * 返回MainRecyclerViewHolder对象
     *
     * @param parent
     * @param viewType
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler_view_demo, parent, false)
        return MainRecyclerViewHolder(view)
    }

    /**
     * 通过Holder对象。以及Item的position的。
     * 然后通过ViewHolder对象获取对应的Item的子View
     *
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: MainRecyclerViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder() called with: position = $position")
        Glide.with(context).load(R.drawable.ic_avatar).into(holder.mImageView)
        holder.mTVTitle.text = dataList[position]
        holder.mTVContent.text = dataList[position]
        holder.mImageView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    /**
     * 在RecyclerView的里面删除对应位置的元素
     */
    fun remove(position: Int) {
        dataList.removeAt(position)
        notifyItemRemoved(position)
    }

    /**
     * 在RecyclerView的里面增加对应位置的元素
     */
    fun add(text: String, position: Int) {
        Log.d(TAG, "add() called with: text = $text, position = $position")
        dataList.add(position, text)
        notifyItemInserted(position)
    }

    fun change(text: String, position: Int) {
        dataList[position] = text
        notifyItemChanged(position)
    }

    class MainRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mImageView: ImageView = itemView.findViewById(R.id.imageView)
        var mTVTitle: TextView = itemView.findViewById(R.id.tvTitle)
        var mTVContent: TextView = itemView.findViewById(R.id.tvContent)

    }

    companion object {
        private const val TAG = "MainRecyclerViewAdapter"
    }
}