package com.frewen.android.demo.business.adapter

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.frewen.android.demo.R
import com.frewen.android.demo.extention.inflate
import com.frewen.android.demo.logic.model.data.CommunityRecommendModel
import com.frewen.android.demo.business.samples.navigation.annotation.recommend.content.EyeRecommendFragment
import com.frewen.aura.toolkits.ktx.ext.gone
import com.frewen.demo.library.adapter.holder.EmptyViewHolder
import de.hdodenhof.circleimageview.CircleImageView

/**
 * @filename: EyeCommendAdapter
 * @introduction: 推荐页面的开眼推荐页面的Adapter
 * @author: Frewen.Wong
 * @time: 2020/10/25 14:31
 * @version 1.0.0
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class EyeCommendAdapter(val context: Context, var dataList: List<CommunityRecommendModel.Item>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object {
        // 原始数据中的Item的布局类型
        const val ITEM_TYPE_STR_HORIZONTAL_SCROLL_CARD = "horizontalScrollCard"
        const val ITEM_TYPE_STR_COMMUNITY_COLUMNS_CARD = "communityColumnsCard"

        // 原始数据中的Data的布局类型
        const val STR_HORIZONTAL_SCROLL_CARD_DATA_TYPE = "HorizontalScrollCard"
        const val STR_ITEM_COLLECTION_DATA_TYPE = "ItemCollection"
        const val STR_FOLLOW_CARD_DATA_TYPE = "FollowCard"

        // 对应Adapter的布局中的类型
        const val HORIZONTAL_SCROLL_CARD_ITEM_COLLECTION_TYPE = 1   //type:horizontalScrollCard -> dataType:ItemCollection
        const val HORIZONTAL_SCROLL_CARD_TYPE = 2                   //type:horizontalScrollCard -> dataType:HorizontalScrollCard
        const val FOLLOW_CARD_TYPE = 3                             //type:communityColumnsCard -> dataType:FollowCard
    }

    override fun getItemViewType(position: Int): Int {
        val item = dataList[position]
        return when (item.type) {
            ITEM_TYPE_STR_HORIZONTAL_SCROLL_CARD -> {
                when (item.data.dataType) {
                    STR_ITEM_COLLECTION_DATA_TYPE -> HORIZONTAL_SCROLL_CARD_ITEM_COLLECTION_TYPE
                    STR_HORIZONTAL_SCROLL_CARD_DATA_TYPE -> HORIZONTAL_SCROLL_CARD_TYPE
                    else -> -1
                }
            }
            ITEM_TYPE_STR_COMMUNITY_COLUMNS_CARD -> {
                if (item.data.dataType == STR_FOLLOW_CARD_DATA_TYPE) FOLLOW_CARD_TYPE
                else -1
            }
            else -> -1
        }
    }

    /**
     * 进行创建ViewHolder的对象
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HORIZONTAL_SCROLL_CARD_ITEM_COLLECTION_TYPE -> {
                HorizontalScrollCardItemCollectionViewHolder(R.layout.item_eye_horizontal_scrollcard_item_collection_type.inflate(parent))
            }
            FOLLOW_CARD_TYPE -> {
                FollowCardViewHolder(R.layout.item_eye_columns_card_follow_card_type.inflate(parent))
            }
            else -> {
                EmptyViewHolder(View(parent.context))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]
        when (holder) {
            is FollowCardViewHolder -> {
                holder.tvChoiceness.gone()
                holder.ivPlay.gone()
                holder.ivLayers.gone()

            }
            else -> {
                holder.itemView.gone()
            }
        }
    }

    override fun getItemCount() = dataList.size


    /**
     * 主题创作广场+话题讨论大厅……
     */
    inner class HorizontalScrollCardItemCollectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
    }


    inner class FollowCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivBgPicture: ImageView = view.findViewById(R.id.ivBgPicture)
        val tvChoiceness: TextView = view.findViewById(R.id.tvChoiceness)
        val ivLayers: ImageView = view.findViewById(R.id.ivLayers)
        val ivPlay: ImageView = view.findViewById(R.id.ivPlay)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val ivAvatar: ImageView = view.findViewById(R.id.ivAvatar)
        val ivRoundAvatar: CircleImageView = view.findViewById(R.id.ivRoundAvatar)
        val tvNickName: TextView = view.findViewById(R.id.tvNickName)
        val tvCollectionCount: TextView = view.findViewById(R.id.tvCollectionCount)
    }


    /**
     * 社区整个垂直列表的间隙
     */
    class ItemDecoration(val fragment: EyeRecommendFragment) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val spanIndex = (view.layoutParams as StaggeredGridLayoutManager.LayoutParams).spanIndex

            outRect.top = fragment.bothSideSpace

            when (spanIndex) {
                0 -> {
                    outRect.left = fragment.bothSideSpace
                    outRect.right = fragment.middleSpace
                }
                else -> {
                    outRect.left = fragment.middleSpace
                    outRect.right = fragment.bothSideSpace
                }
            }
        }
    }
}