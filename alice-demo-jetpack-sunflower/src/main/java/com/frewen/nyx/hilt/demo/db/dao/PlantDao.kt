package com.frewen.nyx.hilt.demo.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.frewen.nyx.hilt.demo.db.entity.Plant
import kotlinx.coroutines.flow.Flow

/**
 * @filename: PlantDao
 * @author: Frewen.Wong
 * @time: 5/5/21 11:05 AM
 * @version: 1.0.0
 * @introduction:  ROOM数据库来访问Plant对象的DAO（数据访问对象）。
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
@Dao
interface PlantDao {
//    @Query("SELECT * FROM plants ORDER BY name")
//    fun getPlants(): Flow<List<Plant>>
//
//    @Query("SELECT * FROM plants WHERE growZoneNumber = :growZoneNumber ORDER BY name")
//    fun getPlantsWithGrowZoneNumber(growZoneNumber: Int): Flow<List<Plant>>
//
//    @Query("SELECT * FROM plants WHERE id = :plantId")
//    fun getPlant(plantId: String): Flow<Plant>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(plants: List<Plant>)
}
