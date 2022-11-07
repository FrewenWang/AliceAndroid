package com.frewen.nyx.hilt.demo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.frewen.nyx.hilt.demo.constants.DATABASE_NAME
import com.frewen.nyx.hilt.demo.db.entity.Log
import com.frewen.nyx.hilt.demo.db.dao.LogDBDao
import com.frewen.nyx.hilt.demo.db.dao.PlantDao
import com.frewen.nyx.hilt.demo.workers.SeedDatabaseWorker

/**
 * 使用Room的数据库，继承自RoomDatabase
 *
 */
@Database(entities = [Log::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    /**
     * 使用伴生对象来构建AppDataBase的单例对象
     */
    companion object {
        
        // For Singleton instantiation
        @Volatile
        private var instance: AppDataBase? = null
        
        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }
        
        private fun buildDatabase(context: Context): AppDataBase {
            return Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                )
                .build()
        }
        
    }
    
    /**
     * 提供LogDBDao独享。
     * LogDBDao使用来数据库访问对象。用来访问logs本地
     */
    abstract fun logDao(): LogDBDao
    
    abstract fun plantDao(): PlantDao
}