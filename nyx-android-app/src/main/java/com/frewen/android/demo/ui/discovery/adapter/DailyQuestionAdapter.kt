package com.frewen.android.demo.ui.discovery.adapter

import android.text.TextUtils
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.frewen.android.demo.R
import com.frewen.android.demo.model.DailyQuestionModel


/**
 * @filename: DailyQuestionAdapter
 * @introduction:
 *             在Kotlin中的一个类可以有一个主构造函数以及一个或多个次构造函数。
 *             主构造函数是类头的一部分：它跟在类名（与可选的类型参数）后。
 *             如果主构造函数没有任何注解或者可见性修饰符，可以省略这个 constructor 关键字。
 *             主构造函数不能包含任何的代码。
 *             初始化的代码可以放到以init关键字作为前缀的初始化块(initializer blocks)中。
 * @author: Frewen.Wong
 * @time: 2020/10/18 16:29
 * @version 1.0.0
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class DailyQuestionAdapter(dataList: MutableList<DailyQuestionModel>?)
    : BaseDelegateMultiAdapter<DailyQuestionModel, BaseViewHolder>(dataList) {

    private val article: Int = 1
    private val project: Int = 2

    private var showTag: Boolean = false//是否展示标签 tag 一般主页才用的到

    init {
        // 第一步，设置代理
        setMultiTypeDelegate(object : BaseMultiTypeDelegate<DailyQuestionModel>() {
            override fun getItemType(data: List<DailyQuestionModel>, position: Int): Int {
                return if (TextUtils.isEmpty(data[position].envelopePic)) article else project
            }
        })
        // 第二步，绑定 item 类型
        getMultiTypeDelegate()?.let {
            it.addItemType(article, R.layout.item_layout_daily_question)
            it.addItemType(project, R.layout.item_layout_daily_question)
        }
    }

    /**
     * 次构造函数
     * 类也可以声明前缀有constructor的次构造函数：
     * 如果类有一个主构造函数，每个次构造函数需要委托给主构造函数，
     * 可以直接委托或者通过别的次构造函数间接委托。
     * 委托到同一个类的另一个构造函数用 this 关键字即可：
     *
     * 请注意，初始化块中的代码实际上会成为主构造函数的一部分。
     * 委托给主构造函数会作为次构造函数的第一条语句，
     * 因此所有初始化块与属性初始化器中的代码都会在次构造函数体之前执行。
     * 即使该类没有主构造函数，这种委托仍会隐式发生，并且仍会执行初始化块：
     */
    constructor(data: MutableList<DailyQuestionModel>?, showTag: Boolean) : this(data) {
        this.showTag = showTag
    }

    override fun convert(holder: BaseViewHolder, item: DailyQuestionModel) {
        when (holder.itemViewType) {
            article -> {
                item.run {
                    holder.setText(
                            R.id.item_home_author,
                            if (author.isNotEmpty()) author else shareUser
                    )
                }

            }
            project -> {
                item.run {
                    holder.setText(
                            R.id.item_home_author,
                            if (author.isNotEmpty()) author else shareUser
                    )
                }
            }
        }
    }

}