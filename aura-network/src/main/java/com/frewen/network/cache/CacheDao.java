package com.frewen.network.cache;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;
import retrofit2.http.Query;

/**
 * @filename: CacheDao
 * @introduction: 数据接口层，用来操作数据库的功能类
 * @author: Frewen.Wong
 * @time: 2020/6/21 00:36
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@Dao
public interface CacheDao {

    /**
     * OnConflictStrategy 插入冲突的处理策略：
     * REPLACE：替换旧的数据，直接继续进行事务处理
     * ROLLBACK：出现冲突，回滚事务。已过时，已经不适用当前的SQLite的绑定.使用ABORT代替
     * ABORT：终止事务，进行事务回滚
     * FAIL：事务异常，已过时，还是建议使用ABORT
     * IGNORE：忽略冲突
     *
     * @param cacheBean
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long save(CacheBean cacheBean);

    /**
     * 注意，冒号后面必须紧跟参数名，中间不能有空格。大于小于号和冒号中间是有空格的。
     * select *from cache where【表中列名】 =:【参数名】------>等于
     * where 【表中列名】 < :【参数名】 小于
     * where 【表中列名】 between :【参数名1】 and :【参数2】------->这个区间
     * where 【表中列名】like :参数名----->模糊查询
     * where 【表中列名】 in (:【参数名集合】)---->查询符合集合内指定字段值的记录
     * 如果是一对多,这里可以写List<Cache>
     *
     * @param key
     *
     * @return CacheBean
     */
    CacheBean getCache(String key);

    /**
     * 只能传递对象昂,删除时根据Cache中的主键 来比对的
     *
     * @param cacheBean
     */
    @Delete
    int delete(CacheBean cacheBean);

    /**
     * 只能传递对象昂,更新时根据Cache中的主键 来比对的
     *
     * @param cacheBean
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(CacheBean cacheBean);

}
