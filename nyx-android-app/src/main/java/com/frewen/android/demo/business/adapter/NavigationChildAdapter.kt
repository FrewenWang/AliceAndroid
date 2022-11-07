package com.frewen.android.demo.business.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.frewen.android.demo.R
import com.frewen.android.demo.business.model.ArticleModel
import com.frewen.android.demo.utils.AppThemeUtil
import com.frewen.aura.toolkits.ktx.ext.toHtml

class NavigationChildAdapter(data: ArrayList<ArticleModel>) :
    BaseQuickAdapter<ArticleModel, BaseViewHolder>(R.layout.widget_flow_button, data) {

    override fun convert(holder: BaseViewHolder, item: ArticleModel) {
        holder.setText(R.id.flow_tag, item.title.toHtml())
        holder.setTextColor(R.id.flow_tag, AppThemeUtil.randomColor())
    }

}