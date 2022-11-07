package com.frewen.nyx.hilt.demo.module

import android.content.Context
import androidx.room.Room
import com.frewen.nyx.hilt.demo.db.AppDataBase
import com.frewen.nyx.hilt.demo.db.dao.LogDBDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * @filename: DataBaseModule
 * @introduction:
 *          接口不是无法通过构造函数注入类型的唯一一种情况。如果某个类不归您所有（
 *          因为它来自外部库，如 Retrofit、OkHttpClient 或 Room 数据库等类）
 *          ，或者必须使用构建器模式创建实例，也无法通过构造函数注入。
 *          有些第三方依赖的框架使用，我们无法在他们的构造函数的里面添加@Inject注解。
 *          但是我们也想让他通过依赖注入来提供他们的实例对象
 *          这样的情况下，我们可以使用 @Provides来标记他们的提供函数
 * @author: Frewen.Wong
 * @time: 2020/8/14 00:01
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@InstallIn(ApplicationComponent::class)
@Module
object DataBaseModule {

    /**
     * 带有@Provides注解的函数会向Hilt提供以下信息：
     *      函数返回类型会告知 Hilt 函数提供哪个类型的实例。
     *      函数参数会告知 Hilt 相应类型的依赖项。
     *      函数主体会告知 Hilt 如何提供相应类型的实例。每当需要提供该类型的实例时，Hilt 都会执行函数主体。
     *
     * 我们可以注意一下@ApplicationContext。 他也是Hilt的通过依赖注入的appContext来机型注入Application的上下文对象
     *
     *
     */
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(
                appContext,
                AppDataBase::class.java,
                "logging.db"
        ).build()
    }

    @Provides
    fun provideLogDao(database: AppDataBase): LogDBDao {
        return database.logDao()
    }
}