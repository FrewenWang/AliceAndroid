package com.frewen.nyx.hilt.demo.respository

import com.frewen.nyx.hilt.demo.db.dao.GardenPlantingDao
import com.frewen.nyx.hilt.demo.db.entity.GardenPlanting
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GardenPlantingRepository @Inject constructor(
        private val gardenPlantingDao: GardenPlantingDao) {
    
    suspend fun createGardenPlanting(plantId: String) {
        val gardenPlanting = GardenPlanting(plantId)
        gardenPlantingDao.insertGardenPlanting(gardenPlanting)
    }
    
    suspend fun removeGardenPlanting(gardenPlanting: GardenPlanting) {
        gardenPlantingDao.deleteGardenPlanting(gardenPlanting)
    }
    
    fun isPlanted(plantId: String) =
            gardenPlantingDao.isPlanted(plantId)
    
    fun getPlantedGardens() = gardenPlantingDao.getPlantedGardens()
}
