package com.frewen.router.annotation

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * @filename: Module
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/1/9 0009 下午10:07
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@Retention(RetentionPolicy.CLASS)
annotation class Module(val value: String)