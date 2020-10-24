package com.frewen.android.demo.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @filename: TencentBaseResponse
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/5/24 12:58
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
open class TencentBaseResponse {
    @SerializedName("showapi_res_code")
    @Expose
    var showapiResCode: Int? = null

    @SerializedName("showapi_res_error")
    @Expose
    var showapiResError: String? = null
}