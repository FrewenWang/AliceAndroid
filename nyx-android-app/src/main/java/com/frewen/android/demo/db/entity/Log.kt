package com.frewen.android.demo.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 定义数据库的数据库表
 */
@Entity(tableName = "logs")
data class Log(val msg: String, val timestamp: Long) {
    /**
     * 定义主键ID
     */
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}