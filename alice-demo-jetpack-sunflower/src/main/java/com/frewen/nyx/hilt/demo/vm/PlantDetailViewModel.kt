package com.frewen.nyx.hilt.demo.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.frewen.nyx.hilt.demo.respository.GardenPlantingRepository
import com.frewen.nyx.hilt.demo.respository.PlantRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

/**
 * @filename: PlantDetailViewModel
 * @author: Frewen.Wong
 * @time: 4/3/21 7:00 PM
 * @version: 1.0.0
 * @introduction:  Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
class PlantDetailViewModel @AssistedInject constructor(
        plantRepository: PlantRepository,
        private val gardenPlantingRepository: GardenPlantingRepository,
        @Assisted private val plantId: String) : ViewModel() {
    
    val isPlanted = gardenPlantingRepository.isPlanted(plantId).asLiveData()
    val plant = plantRepository.getPlant(plantId).asLiveData()
}

/**
 * 通过{@link AssistedInject}构造函数注释用于创建类型实例的抽象类或接口。
 */
@AssistedFactory
interface PlantDetailViewModelFactory {
    fun create(plantId: String): PlantDetailViewModel
}
