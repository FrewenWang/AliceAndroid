package com.frewen.rounter.compiler

import com.frewen.rounter.compiler.utils.EnvironmentManager
import com.frewen.router.annotation.Module
import com.frewen.router.annotation.Modules
import javax.annotation.processing.*
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

/**
 * @filename: RouterProcessor
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/1/9 0009 下午10:11
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class RouterProcessor : AbstractProcessor() {
    private val DEBUG = true
    private var mFiler: Filer? = null
    private var messager: Messager? = null

    /**
     * 注解执行器的init方法
     */
    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        messager = processingEnv.messager
        mFiler = processingEnv.filer
        // 初始化EnvironmentManager对象
        EnvironmentManager.environment.init(processingEnv)
    }


    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {
        // 注解为 null，直接返回
        // 注解为 null，直接返回
        if (annotations == null || annotations.size == 0) {
            false
        }


        EnvironmentManager.environment.messager.printMessage(Diagnostic.Kind.NOTE, "process")
        // 判断是否有Module注解、或者Modules注解
        var hasModule = false
        var hasModules = false

        // module
        var moduleName = "RouterMapping"
        val moduleList: Set<Element?> = roundEnv.getElementsAnnotatedWith(Module::class.java)
        if (moduleList.isNotEmpty()) {
            val annotation = moduleList.iterator().next()!!.getAnnotation(Module::class.java)
            moduleName = moduleName + "_" + annotation.value
            hasModule = true
        }

        // modules
        // modules
        var moduleNames: Array<out String>? = null
        val modulesList: Set<Element> = roundEnv.getElementsAnnotatedWith(Modules::class.java)
        if (modulesList.isNotEmpty()) {
            val modules = modulesList.iterator().next()
            moduleNames = modules.getAnnotation(Modules::class.java).value
            hasModules = true
        }

        debug("generate modules RouterInit annotations=$annotations roundEnv=$roundEnv")
        debug("generate modules RouterInit hasModules=$hasModules hasModule=$hasModule")

        // RouterInit
        if (hasModules) { // 有使用 @Modules 注解，生成 RouterInit 文件，适用于多个 moudle
            debug("generate modules RouterInit")
            generateModulesRouterInit(moduleNames)
        } else if (!hasModule) { // 没有使用 @Modules 注解，并且没有使用 @Module，生成相应的 RouterInit 文件，使用与单个 moudle
            debug("generate default RouterInit")
            //generateDefaultRouterInit()
        }
        return false
    }

    /**
     * 生成Modules路由初始化操作
     */
    private fun generateModulesRouterInit(moduleNames: Array<out String>?) {
//        val initMethod: MethodSpec.Builder = MethodSpec.methodBuilder("init")
//                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
    }

    private fun debug(msg: String) {
        if (DEBUG) {
            messager!!.printMessage(Diagnostic.Kind.NOTE, msg)
        }
    }
}