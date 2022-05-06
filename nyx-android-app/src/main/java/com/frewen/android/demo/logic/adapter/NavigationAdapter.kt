package com.frewen.android.demo.logic.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.frewen.android.demo.R
import com.frewen.android.demo.logic.model.ArticleModel
import com.frewen.android.demo.logic.model.NavigationModel
import com.frewen.aura.toolkits.ktx.ext.toHtml

class NavigationAdapter(data: ArrayList<NavigationModel>) :
    BaseQuickAdapter<NavigationModel, BaseViewHolder>(R.layout.item_navigation_list, data) {

    private var navigationAction: (item: ArticleModel, view: View) -> Unit =
        { _: ArticleModel, _: View -> }

    override fun convert(holder: BaseViewHolder, item: NavigationModel) {
        holder.setText(R.id.item_navigation_title, item.name.toHtml())
        holder.getView<RecyclerView>(R.id.item_navigation_rv).run {
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            adapter = NavigationChildAdapter(item.articles).apply {
                setOnItemClickListener { _, view, position ->
                    navigationAction.invoke(item.articles[position], view)
                }
            }
        }
    }

    fun setNavigationAction(inputNavigationAction: (item: ArticleModel, view: View) -> Unit) {
        this.navigationAction = inputNavigationAction
    }
}