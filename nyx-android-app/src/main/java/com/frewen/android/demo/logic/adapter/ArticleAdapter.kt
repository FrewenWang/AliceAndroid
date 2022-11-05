package com.frewen.android.demo.logic.adapter

import android.text.TextUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.frewen.android.demo.R
import com.frewen.android.demo.logic.model.ArticleModel
import com.frewen.android.demo.utils.AppThemeUtil
import com.frewen.aura.toolkits.ktx.ext.toHtml
import com.frewen.aura.ui.view.heart.HeartCollectView

/**
 * @filename: ArticleAdapter
 * @author: Frewen.Wang
 * @time: 2/12/21 3:50 PM
 * @version: 1.0.0
 * @introduction: 请求文章内容的Adapter
 * @copyright: Copyright ©2021 Frewen.Wang. All Rights Reserved.
 */
class ArticleAdapter(data: MutableList<ArticleModel>?) :
    BaseDelegateMultiAdapter<ArticleModel, BaseViewHolder>(data) {
    private val Article = 1//文章类型
    private val Project = 2//项目类型
    private var showTag = false//是否展示标签 tag 一般主页才用的到

    private var collectAction: (item: ArticleModel, v: HeartCollectView, position: Int) -> Unit =
        { _: ArticleModel, _: HeartCollectView, _: Int -> }

    constructor(data: MutableList<ArticleModel>?, showTag: Boolean) : this(data) {
        this.showTag = showTag
    }

    override fun convert(helper: BaseViewHolder, item: ArticleModel) {
        when (helper.itemViewType) {
            Article -> {
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
            Project -> {
                //项目布局的赋值
                item.run {
                    helper.setText(
                        R.id.item_project_author,
                        if (author.isNotEmpty()) author else shareUser
                    )
                    helper.setText(R.id.item_project_title, title.toHtml())
                    helper.setText(R.id.item_project_content, desc.toHtml())
                    helper.setText(
                        R.id.item_project_type,
                        "$superChapterName·$chapterName".toHtml()
                    )
                    helper.setText(R.id.item_project_date, niceDate)
                    if (showTag) {
                        //展示标签
                        helper.setGone(R.id.item_project_new, !fresh)
                        helper.setGone(R.id.item_project_top, type != 1)
                        if (tags.isNotEmpty()) {
                            helper.setGone(R.id.item_project_type1, false)
                            helper.setText(R.id.item_project_type1, tags[0].name)
                        } else {
                            helper.setGone(R.id.item_project_type1, true)
                        }
                    } else {
                        //隐藏所有标签
                        helper.setGone(R.id.item_project_top, true)
                        helper.setGone(R.id.item_project_type1, true)
                        helper.setGone(R.id.item_project_new, true)
                    }
                    helper.getView<HeartCollectView>(R.id.item_project_collect).isChecked = collect
                    Glide.with(context).load(envelopePic)
                        .transition(DrawableTransitionOptions.withCrossFade(500))
                        .into(helper.getView(R.id.item_project_imageview))
                }
                helper.getView<HeartCollectView>(R.id.item_project_collect)
                    .setOnCollectViewClickListener(object :
                        HeartCollectView.OnCollectViewClickListener {
                        override fun onClick(v: HeartCollectView) {
                            collectAction.invoke(item, v, helper.adapterPosition)
                        }
                    })
            }
        }
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
        // 第一步，设置代理
        setMultiTypeDelegate(object : BaseMultiTypeDelegate<ArticleModel>() {
            override fun getItemType(data: List<ArticleModel>, position: Int): Int {
                return if (TextUtils.isEmpty(data[position].envelopePic)) Article else Project
            }
        })
        // 第二步，绑定 item 类型
        getMultiTypeDelegate()?.let {
            it.addItemType(Article, R.layout.item_article)
            it.addItemType(Project, R.layout.item_project)
        }
    }

}