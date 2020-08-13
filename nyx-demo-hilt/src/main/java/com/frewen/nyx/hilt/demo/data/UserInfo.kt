package com.frewen.nyx.hilt.demo.data

import javax.inject.Inject

/**
 * @filename: UserInfo
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/8/12 21:07
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
data class UserInfo constructor(var id: Int = 10001, var name: String = "Frewen.Wong", var age: Int = 25) {

    @Inject
    constructor() : this(10002, "Frewen.Zhang", 30)


}