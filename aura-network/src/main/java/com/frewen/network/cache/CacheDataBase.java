package com.frewen.network.cache;

import com.frewen.aura.toolkits.core.AuraToolKits;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * @filename: CacheDataBase
 * @introduction: 这个就是我们进行创建数据库的实体类
 *         必须要有的一个注解：Database 里面有三个参数：entities这个就是我们创建我们的数据库表
 *         version是数据库的版本，也是后续数据库升级的重要依据
 *         exportSchema 数据在升级或者执行的操作，帮我们生成Json文件
 *         我们需要在gradle.build文件添加Json文件的存储路径
 *         javaCompileOptions {
 *         // 这个是设置注解执行器的选项参数，我们来设置room里面schemas的存储路径
 *         annotationProcessorOptions {
 *         arguments = ["room.schemaLocation": "$projectDir/schemas".toString()]
 *         }
 *         }
 * @author: Frewen.Wong
 * @time: 2020/6/21 00:25
 * @version: 1.0.0
 * @copyright Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@Database(entities = {CacheBean.class}, version = 1, exportSchema = true)
public abstract class CacheDataBase extends RoomDatabase {

    private static CacheDataBase mCacheDataBase;
    /**
     *
     */
    private static Migration sMigration = new Migration(1, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // database.execSQL("alter table user rename ");
        }
    };

    static {
        // 创建一个内存数据库
        // 但是这种数据库只存在于内存中，也就是说进程被杀数据丢失
        // Room.inMemoryDatabaseBuilder(AuraToolKits.getAppContext(), null);
        // 所以我们一般选择下面的创建ROOM数据库的方式: 数据库名称：AuraNetWorkDB
        mCacheDataBase = Room.databaseBuilder(AuraToolKits.getAppContext(), CacheDataBase.class, "AuraNetWorkDB")
                // 是否允许在主线程查询，默认是false。如果在主线程操作，会抛出异常
                .allowMainThreadQueries()
                // 数据库被创建和打开的回调
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                    }

                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
                    }

                    @Override
                    public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
                        super.onDestructiveMigration(db);
                    }
                })
                // 设置查询数据库的线程池
                // .setQueryExecutor(null)
                // 设置数据库操作的工厂类
                // .openHelperFactory()
                // 设置room的日志模式
                // .setJournalMode()
                // 设置数据库升级之后的异常之后的回滚
                .fallbackToDestructiveMigration()
                // 数据库升级异常之后,来根据指定版本回滚
                //.fallbackToDestructiveMigrationFrom()
                .addMigrations(sMigration)
                .build();

    }
}
