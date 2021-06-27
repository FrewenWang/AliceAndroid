package com.frewen.android.demo.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.frewen.android.demo.R
import com.frewen.aura.toolkits.display.DisplayHelper
import com.yanzhenjie.recyclerview.SwipeRecyclerView

/**
 * @filename: DefineLoadMoreView
 * @author: Frewen.Wong
 * @time: 2021/6/27 14:37
 * @version: 1.0.0
 * @introduction:  Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
class DefineLoadMoreView(context: Context) : LinearLayout(context),
    SwipeRecyclerView.LoadMoreView, View.OnClickListener {
    
    private val mProgressBar: ProgressBar
    private val mTvMessage: TextView
    private var mLoadMoreListener: SwipeRecyclerView.LoadMoreListener? = null
    
    init {
        layoutParams = ViewGroup.LayoutParams(-1, -2)
        gravity = Gravity.CENTER
        visibility = View.GONE
        val minHeight = DisplayHelper.dip2px(36f)
        minimumHeight = minHeight
        View.inflate(context, R.layout.layout_foot_load_more_view, this)
        mProgressBar = findViewById(R.id.loading_view)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mProgressBar.indeterminateTintMode = PorterDuff.Mode.SRC_ATOP
            // mProgressBar.indeterminateTintList = SettingUtil.getOneColorStateList(context)
        }
        mTvMessage = findViewById(R.id.tv_message)
        setOnClickListener(this)
    }
    
    override fun onLoading() {
        visibility = View.VISIBLE
        mProgressBar.visibility = View.VISIBLE
        mTvMessage.visibility = View.VISIBLE
        mTvMessage.text = "正在努力加载，请稍后"
    }
    
    override fun onLoadFinish(dataEmpty: Boolean, hasMore: Boolean) {
        if (!hasMore) {
            visibility = View.VISIBLE
            
            if (dataEmpty) {
                mProgressBar.visibility = View.GONE
                mTvMessage.visibility = View.VISIBLE
                mTvMessage.text = "暂时没有数据"
            } else {
                mProgressBar.visibility = View.GONE
                mTvMessage.visibility = View.VISIBLE
                mTvMessage.text = "没有更多数据啦"
            }
        } else {
            visibility = View.INVISIBLE
        }
    }
    
    override fun onWaitToLoadMore(loadMoreListener: SwipeRecyclerView.LoadMoreListener?) {
        this.mLoadMoreListener = loadMoreListener
        visibility = View.VISIBLE
        mProgressBar.visibility = View.GONE
        mTvMessage.visibility = View.VISIBLE
        mTvMessage.text = "点我加载更多"
    }
    
    override fun onLoadError(errorCode: Int, errorMessage: String?) {
        visibility = View.VISIBLE
        mProgressBar.visibility = View.GONE
        mTvMessage.visibility = View.VISIBLE
        // 这里要不直接设置错误信息，要不根据errorCode动态设置错误数据。
        mTvMessage.text = errorMessage
    }
    
    override fun onClick(v: View?) {
        //为什么加后面那个判断，因为Wandroid第0页能够请求完所有数据的情况下， 再去请求第1页 也能取到值，
        // 所以这里要判断没有更多数据的时候禁止在响应点击事件了,同时在加载中时也不能触发加载更多的监听
        mLoadMoreListener?.let {
            if (mTvMessage.text != "没有更多数据啦" && mProgressBar.visibility != View.VISIBLE) {
                it.onLoadMore()
            }
        }
    }
    
    fun setLoadViewColor(colorstatelist: ColorStateList) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mProgressBar.indeterminateTintMode = PorterDuff.Mode.SRC_ATOP
            mProgressBar.indeterminateTintList = colorstatelist
        }
    }
    
    fun setLoadMoreListener(mLoadMoreListener: SwipeRecyclerView.LoadMoreListener) {
        this.mLoadMoreListener = mLoadMoreListener
    }
    
}