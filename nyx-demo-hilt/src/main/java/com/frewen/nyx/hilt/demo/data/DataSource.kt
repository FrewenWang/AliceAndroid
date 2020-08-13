package com.frewen.nyx.hilt.demo.data

import com.frewen.nyx.hilt.demo.db.entity.Log


/**
 * @filename: DataSource
 * @introduction:
 *          关于数据库的方法的最外层访问的方法，我们抽象成一个接口。
 *          并且我们提供两个实现类
 * @author: Frewen.Wong
 * @time: 2020/8/13 00:24
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
interface DataSource {
    fun insertLog(msg: String)
    fun queryAllLogs(callback: (List<Log>) -> Unit)
    fun removeLogs()
}