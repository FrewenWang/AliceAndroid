package com.frewen.android.demo.business.adapter

import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.frewen.android.demo.R
import com.frewen.android.demo.business.model.ArticleModel
import com.frewen.android.demo.utils.AppThemeUtil
import com.frewen.aura.toolkits.ktx.ext.toHtml
import com.frewen.aura.ui.view.heart.HeartCollectView

/**
 * @filename: ArticleAdapter
 * @author: Frewen.Wang
 * @time: 2/12/21 3:50 PM
 * @version: 1.0.0
 * @introduction: Android基础Demo程序的Adapter
 * @copyright: Copyright ©2021 Frewen.Wang. All Rights Reserved.
 */
class BasicDemoAdapter(data: MutableList<ArticleModel>?) :
    BaseDelegateMultiAdapter<ArticleModel, BaseViewHolder>(data) {
    private var showTag = false//是否展示标签 tag 一般主页才用的到

    private var collectAction: (item: ArticleModel, v: HeartCollectView, position: Int) -> Unit =
        { _: ArticleModel, _: HeartCollectView, _: Int -> }

    constructor(data: MutableList<ArticleModel>?, showTag: Boolean) : this(data) {
        this.showTag = showTag
    }

    override fun convert(helper: BaseViewHolder, item: ArticleModel) {
        //文章布局的赋值
        item.run {
            helper.setText(
                R.id.item_home_author,
                if (author.isNotEmpty()) author else shareUser
            )
            helper.setText(R.id.item_home_content, title.toHtml())
            helper.setText(R.id.item_home_type2, "$superChapterName·$chapterName".toHtml())
            helper.setText(R.id.item_home_date, niceDate)

            // helper.getView<CollectView>(R.id.item_home_collect).isChecked = collect
            if (showTag) {
                //展示标签
                helper.setGone(R.id.item_home_new, !fresh)
                helper.setGone(R.id.item_home_top, type != 1)
                if (tags.isNotEmpty()) {
                    helper.setGone(R.id.item_home_type1, false)
                    helper.setText(R.id.item_home_type1, tags[0].name)
                } else {
                    helper.setGone(R.id.item_home_type1, true)
                }
            } else {
                //隐藏所有标签
                helper.setGone(R.id.item_home_top, true)
                helper.setGone(R.id.item_home_type1, true)
                helper.setGone(R.id.item_home_new, true)
            }
        }
        helper.getView<HeartCollectView>(R.id.item_home_collect)
            .setOnCollectViewClickListener(object :
                HeartCollectView.OnCollectViewClickListener {
                override fun onClick(v: HeartCollectView) {
                    collectAction.invoke(item, v, helper.adapterPosition)
                }
            })
    }

    init {
        // 获取设置里面持久化缓存的列表动画模式
        val animMode = AppThemeUtil.getListAnimMode()
        //等于0，关闭列表动画 否则开启
        if (animMode == 0) {
            this.animationEnable = false
        } else {
            this.animationEnable = true
            this.setAnimationWithDefault(AnimationType.values()[animMode - 1])
        }
    }

}