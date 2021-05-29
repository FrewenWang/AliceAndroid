package com.frewen.nyx.hilt.demo.respository

import com.frewen.nyx.hilt.demo.db.dao.PlantDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 用于处理数据操作的存储库模块。
 *
 * Collecting from the Flows in [PlantDao] is main-safe.  Room supports Coroutines and moves the
 * query execution off of the main thread.
 */
@Singleton
class PlantRepository @Inject constructor(private val plantDao: PlantDao) {
    
    fun getPlants() = plantDao.getPlants()
    
    fun getPlant(plantId: String) = plantDao.getPlant(plantId)
    
    fun getPlantsWithGrowZoneNumber(growZoneNumber: Int) =
            plantDao.getPlantsWithGrowZoneNumber(growZoneNumber)
}
