package com.frewen.nyx.hilt.demo

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.room.Room
import com.frewen.nyx.hilt.demo.db.AppDataBase
import com.frewen.nyx.hilt.demo.data.LoggerDBDataSource
import com.frewen.nyx.hilt.demo.navigation.DemoNavigator
import com.frewen.nyx.hilt.demo.navigation.DemoNavigatorImpl

class RoomServiceProvider(applicationContext: Context) {

    /**
     * 创建应用的本地数据库
     *
     * 数据库存储的地址：/data/data/packageName/databases/AppDataBase.db
     */
    private val appDataBase = Room
            .databaseBuilder(applicationContext, AppDataBase::class.java, "AppDataBase.db")
            .build()

    /**
     *  实例化请求Logs数据库的资源数据
     */
    val loggerLocalDataSource = LoggerDBDataSource(appDataBase.logDao())


    fun provideNavigator(activity: FragmentActivity): DemoNavigator {
        return DemoNavigatorImpl(activity)
    }


}

