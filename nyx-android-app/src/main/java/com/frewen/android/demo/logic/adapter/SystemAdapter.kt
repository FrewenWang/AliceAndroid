package com.frewen.android.demo.logic.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.frewen.android.demo.R
import com.frewen.android.demo.logic.model.SystemModel
import com.frewen.aura.toolkits.ktx.ext.toHtml
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class SystemAdapter(data: ArrayList<SystemModel>) :
    BaseQuickAdapter<SystemModel, BaseViewHolder>(R.layout.item_navigation_list, data) {

    private var method: (data: SystemModel, view: View, position: Int) -> Unit =
        { _: SystemModel, _: View, _: Int -> }

    override fun convert(holder: BaseViewHolder, item: SystemModel) {
        holder.setText(R.id.item_navigation_title, item.name.toHtml())
        holder.getView<RecyclerView>(R.id.item_navigation_rv).run {
            val foxayoutManager: FlexboxLayoutManager by lazy {
                FlexboxLayoutManager(context).apply {
                    //方向 主轴为水平方向，起点在左端
                    flexDirection = FlexDirection.ROW
                    //左对齐
                    justifyContent = JustifyContent.FLEX_START
                }
            }
            layoutManager = foxayoutManager
            setHasFixedSize(true)
            setItemViewCacheSize(200)
            isNestedScrollingEnabled = false
            adapter = SystemChildAdapter(item.children).apply {
                setOnItemClickListener { _, view, position ->
                    method(item, view, position)
                }
            }

        }
    }


    fun setChildClick(method: (data: SystemModel, view: View, position: Int) -> Unit) {
        this.method = method
    }
}