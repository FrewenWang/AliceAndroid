package com.frewen.android.demo.adapter

import android.view.View
import android.view.ViewGroup
import com.frewen.android.demo.R
import com.frewen.android.demo.adapter.ItemViewType.Companion.TEXT_CARD_HEADER4
import com.frewen.android.demo.adapter.ItemViewType.Companion.UNKNOWN
import com.frewen.android.demo.model.Discovery
import com.frewen.demo.library.extention.inflate
import com.frewen.demo.library.ui.holder.EmptyViewHolder
import com.frewen.demo.library.ui.holder.TextCardViewHeader4ViewHolder

/**
 * @filename: RecyclerViewHolderHelper
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/7/24 07:44
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
interface ItemViewType {

    companion object {

        const val UNKNOWN = -1              //未知类型，使用EmptyViewHolder容错处理。

        const val CUSTOM_HEADER = 0         //自定义头部类型。

        const val TEXT_CARD_HEADER1 = 1

        const val TEXT_CARD_HEADER2 = 2

        const val TEXT_CARD_HEADER3 = 3

        const val TEXT_CARD_HEADER4 = 4     //type:textCard -> dataType:TextCard,type:header4

        const val TEXT_CARD_HEADER5 = 5     //type:textCard -> dataType:TextCard -> type:header5

        const val TEXT_CARD_HEADER6 = 6

        const val TEXT_CARD_HEADER7 = 7    //type:textCard -> dataType:TextCardWithRightAndLeftTitle,type:header7

        const val TEXT_CARD_HEADER8 = 8    //type:textCard -> dataType:TextCardWithRightAndLeftTitle,type:header8

        const val TEXT_CARD_FOOTER1 = 9

        const val TEXT_CARD_FOOTER2 = 10    //type:textCard -> dataType:TextCard,type:footer2

        const val TEXT_CARD_FOOTER3 = 11    //type:textCard -> dataType:TextCardWithTagId,type:footer3

        const val BANNER = 12               //type:banner -> dataType:Banner

        const val BANNER3 = 13              //type:banner3-> dataType:Banner

        const val FOLLOW_CARD = 14          //type:followCard -> dataType:FollowCard -> type:video -> dataType:VideoBeanForClient

        const val TAG_BRIEFCARD = 15        //type:briefCard -> dataType:TagBriefCard

        const val TOPIC_BRIEFCARD = 16      //type:briefCard -> dataType:TopicBriefCard

        const val COLUMN_CARD_LIST = 17      //type:columnCardList -> dataType:ItemCollection

        const val VIDEO_SMALL_CARD = 18     //type:videoSmallCard -> dataType:VideoBeanForClient

        const val INFORMATION_CARD = 19     //type:informationCard -> dataType:InformationCard

        const val AUTO_PLAY_VIDEO_AD = 20   //type:autoPlayVideoAd -> dataType:AutoPlayVideoAdDetail

        const val HORIZONTAL_SCROLL_CARD = 21    //type:horizontalScrollCard -> dataType:HorizontalScrollCard

        const val SPECIAL_SQUARE_CARD_COLLECTION = 22   //type:specialSquareCardCollection -> dataType:ItemCollection

        const val UGC_SELECTED_CARD_COLLECTION = 23   //type:ugcSelectedCardCollection -> dataType:ItemCollection

        const val MAX = 100   //避免外部其他类型与此处包含的某个类型重复。
    }
}

object RecyclerViewHolderHelper {
    fun getViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TEXT_CARD_HEADER4 -> TextCardViewHeader4ViewHolder(R.layout.item_text_card_type_header_four.inflate(parent))
        else -> EmptyViewHolder(View(parent.context))
    }


    private fun getItemViewType(type: String, dataType: String) = when (type) {
        else -> UNKNOWN
    }

    /**
     *
     */
    fun getItemViewType(item: Discovery.Item): Int {
        return if (item.type == "textCard") getTextCardType(item.data.type) else getItemViewType(item.type, item.data.dataType)

    }

    /**
     * 获取TextCard的类型
     */
    private fun getTextCardType(type: String) = when (type) {
        "header4" -> TEXT_CARD_HEADER4
        else -> UNKNOWN
    }
}
