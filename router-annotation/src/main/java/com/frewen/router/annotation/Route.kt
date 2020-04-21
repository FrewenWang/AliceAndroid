package com.frewen.router.annotation

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * @filename: Router
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/1/9 0009 下午10:08
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(RetentionPolicy.CLASS)
annotation class Route(val path: String)
