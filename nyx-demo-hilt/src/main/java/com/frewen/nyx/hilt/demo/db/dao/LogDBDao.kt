package com.frewen.nyx.hilt.demo.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.frewen.nyx.hilt.demo.db.entity.Log

/**
 * 数据库访问对象（DAO）用来访问数据库的相关的接口
 */
@Dao
interface LogDBDao {

    @Query("SELECT * FROM logs ORDER BY id DESC")
    fun getAll(): List<Log>

    /**
     * Insert注解
     * 是ROOM数据库的插入的相关注解
     *
     * vararg logs: Log  ：
     */
    @Insert
    fun insertAll(vararg logs: Log)

    @Query("DELETE FROM logs")
    fun nukeTable()

}