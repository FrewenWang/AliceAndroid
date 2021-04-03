package com.frewen.nyx.hilt.demo.data

import com.frewen.nyx.hilt.demo.db.entity.Log
import java.util.*
import javax.inject.Inject

/**
 * 这个是我们DataSource的模块的第一种实现，
 * 将是所有日志信息存储到内存中
 */
class LoggerInMemoryDataSource @Inject constructor() : DataSource {

    private val logs = LinkedList<Log>()

    override fun insertLog(msg: String) {
        logs.addFirst(Log(msg, System.currentTimeMillis()))
    }

    /**
     * 从数据库中查询所有的Logs
     * （List<Log>) -> Unit 高阶函数，函数的入参是List<Log>。返回值是Unit（无返回值）
     */
    override fun queryAllLogs(callback: (List<Log>) -> Unit) {
        callback(logs)
    }


    override fun removeLogs() {
        logs.clear()
    }


}