package com.frewen.nyx.hilt.demo.data

import android.os.Handler
import android.os.Looper
import com.frewen.nyx.hilt.demo.db.entity.Log
import com.frewen.nyx.hilt.demo.db.dao.LogDBDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

/**
 * 这个是我们DataSource的模块的第一种实现，将是所有日志信息通过LogDBDao存储到数据库中
 */
class LoggerDBDataSource @Inject constructor(private val logDao: LogDBDao) : DataSource {

    private val executorService: ExecutorService = Executors.newFixedThreadPool(4)

    /**
     * 单例懒加载来进行初始化MainThreadHandler
     * 这个懒加载是线程安全的
     */
    private val mainThreadHandler by lazy {
        Handler(Looper.getMainLooper())
    }

    override fun insertLog(msg: String) {
        executorService.execute {
            logDao.insertAll(Log(msg, System.currentTimeMillis()))
        }
    }

    /**
     * 从数据库中查询所有的Logs
     * （List<Log>) -> Unit 高阶函数，函数的入参是List<Log>。返回值是Unit（无返回值）
     */
    override fun queryAllLogs(callback: (List<Log>) -> Unit) {
        executorService.execute {
            val logs = logDao.getAll()
            mainThreadHandler.post {
                callback(logs)
            }
        }
    }


    override fun removeLogs() {
        executorService.execute {
            logDao.nukeTable()
        }
    }


}