package com.frewen.rounter.compiler.utils

import javax.annotation.processing.Filer
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

class EnvironmentManager private constructor() {
    /**
     * 一个用来处理TypeMirror的工具类
     */
    private lateinit var typeUtils: Types

    /**
     * 一个用来处理Element的工具类
     */
    private lateinit var elementUtils: Elements
    /**
     * 正如这个名字所示，使用Filer你可以创建文件
     */
    private lateinit var filer: Filer
    /**
     * 日志相关的辅助类
     */
    lateinit var messager: Messager

    fun init(environment: ProcessingEnvironment) {
        typeUtils = environment.typeUtils
        elementUtils = environment.elementUtils
        filer = environment.filer
        messager = environment.messager
    }

    /**
     * 伴生对象
     */
    companion object {
        val environment = EnvironmentManager()
    }
}