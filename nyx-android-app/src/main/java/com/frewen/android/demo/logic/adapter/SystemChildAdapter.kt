package com.frewen.android.demo.logic.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.frewen.android.demo.R
import com.frewen.android.demo.logic.model.ProjectClassifyModel
import com.frewen.android.demo.utils.AppThemeUtil
import com.frewen.aura.toolkits.ktx.ext.toHtml

class SystemChildAdapter(data: ArrayList<ProjectClassifyModel>) :
    BaseQuickAdapter<ProjectClassifyModel, BaseViewHolder>(R.layout.widget_flow_button, data) {

    override fun convert(holder: BaseViewHolder, item: ProjectClassifyModel) {
        holder.setText(R.id.flow_tag, item.name.toHtml())
        holder.setTextColor(R.id.flow_tag, AppThemeUtil.randomColor())
    }

}