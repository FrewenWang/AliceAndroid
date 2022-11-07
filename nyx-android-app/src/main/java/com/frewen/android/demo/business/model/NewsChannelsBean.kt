package com.frewen.android.demo.business.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @filename: NewsChannelsBean
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/5/24 12:59
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class NewsChannelsBean() : TencentBaseResponse() {

    @SerializedName("showapi_res_body")
    @Expose
    var showapiResBody: ShowapiResBody? = null

    class ChannelList {
        @SerializedName("channelId")
        @Expose
        var channelId: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null
    }

    class ShowapiResBody {
        @SerializedName("totalNum")
        @Expose
        var totalNum: Int? = null

        @SerializedName("ret_code")
        @Expose
        var retCode: Int? = null

        @SerializedName("channelList")
        @Expose
        var channelList: List<ChannelList>? = null
    }

}