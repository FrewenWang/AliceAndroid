package com.frewen.router.annotation

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * @filename: Modules
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/1/9 0009 下午10:08
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@Retention(RetentionPolicy.CLASS)
annotation class Modules(vararg val value: String)