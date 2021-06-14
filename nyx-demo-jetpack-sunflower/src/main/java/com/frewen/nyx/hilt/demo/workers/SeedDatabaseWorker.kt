package com.frewen.nyx.hilt.demo.workers

import android.content.Context
import android.util.JsonReader
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.frewen.nyx.hilt.demo.constants.PLANT_DATA_FILENAME
import com.frewen.nyx.hilt.demo.db.AppDataBase
import com.frewen.nyx.hilt.demo.db.entity.Plant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.coroutineScope

/**
 * @filename: SeedDatabaseWorker
 * @author: Frewen.Wong
 * @time: 2021/6/11 07:51
 * @version: 1.0.0
 * @introduction:  Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
class SeedDatabaseWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    companion object {
        private const val TAG = "SeedDatabaseWorker"
    }
    
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(PLANT_DATA_FILENAME).use { inputStream ->
                com.google.gson.stream.JsonReader(inputStream.reader()).use { jsonReader ->
                    val plantType = object : TypeToken<List<Plant>>() {}.type
                    val plantList: List<Plant> = Gson().fromJson(jsonReader, plantType)
                    val database = AppDataBase.getInstance(applicationContext)
                    database.plantDao().insertAll(plantList)
                    
                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }
}