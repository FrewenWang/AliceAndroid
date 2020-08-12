package com.frewen.android.demo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.frewen.android.demo.db.dao.LogDBDao
import com.frewen.android.demo.db.entity.Log

/**
 * 使用Room的数据库，继承自RoomDatabase
 */
@Database(entities = [Log::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    /**
     * 提供LogDBDao独享。
     * LogDBDao使用来数据库访问对象。用来访问logs本地
     */
    abstract fun logDao(): LogDBDao
}