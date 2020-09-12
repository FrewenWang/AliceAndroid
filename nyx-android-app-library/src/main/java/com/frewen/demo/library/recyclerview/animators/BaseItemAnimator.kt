package com.frewen.demo.library.recyclerview.animators

import androidx.recyclerview.widget.SimpleItemAnimator

/**
 * @filename: BaseItemAnimator
 * @introduction:
 *
 * RecyclerView是5.0之后新添加的控件，用于在部分方面取代ListView和GridView。
 * RecyclerView耦合性非常低，它不关心视图相关问题。
 * ItemDivide、LayoutManager、点击事件、动画等都是可以动态添加，
 * 我这次主要来说是RecyclerView.ItemAnimator的分析和自定义。
 * RecyclerView.ItemAnimator主要用于RecyclerView的Item添加、移除、更新时的动画。
 * @author: Frewen.Wong
 * @time: 2020/9/11 19:11
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
abstract class BaseItemAnimator : SimpleItemAnimator() {


}