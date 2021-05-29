package com.frewen.nyx.hilt.demo.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Calendar.DAY_OF_YEAR

/**
 * ROOM声明存入数据库的表名和实体
 */
@Entity(tableName = "plants")
data class Plant(
        // 设置主键和
        @PrimaryKey @ColumnInfo(name = "id") val plantId: String,
        val name: String,
        val description: String,
        val growZoneNumber: Int,
        val wateringInterval: Int = 7, // how often the plant should be watered, in days
        val imageUrl: String = ""
) {
    
    /**
     * Determines if the plant should be watered.  Returns true if [since]'s date > date of last
     * watering + watering Interval; false otherwise.
     */
    fun shouldBeWatered(since: Calendar, lastWateringDate: Calendar) =
            since > lastWateringDate.apply { add(DAY_OF_YEAR, wateringInterval) }
    
    override fun toString() = name
}
