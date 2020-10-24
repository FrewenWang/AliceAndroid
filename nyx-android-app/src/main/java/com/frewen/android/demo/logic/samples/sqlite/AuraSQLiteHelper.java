package com.frewen.android.demo.logic.samples.sqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

import static com.frewen.android.demo.logic.samples.sqlite.SQLiteConstant.SQL_CREATE_TABLE;

/**
 * @filename: DataBaseHelper
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020-03-02 09:30
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class AuraSQLiteHelper extends SQLiteOpenHelper {
    private static final String TAG = "AuraDataBaseHelper";
    /**
     * 建表语句列表
     */
    private List<String> createTableList;
    private String nowDbName;

    /**
     * DataBaseHelper 数据库访问助手的构造函数
     *
     * @param context       上下文对象
     * @param dbName        数据库名称
     * @param version       数据库版本号   要求版本号>=1
     * @param createSqlList 创建创建数据库SQL语句列表
     */
    public AuraSQLiteHelper(@Nullable Context context, @Nullable String dbName,
                            int version, List<String> createSqlList) {
        this(context, dbName, null, version, null, createSqlList);
    }

    /**
     * DataBaseHelper 数据库访问助手的构造函数
     *
     * @param context 上下文对象
     * @param dbName  数据库名称
     * @param factory 数据库游标工厂 这个我们一般不传，传空即可
     * @param version 数据库版本号   要求版本号>=1
     */
    public AuraSQLiteHelper(@Nullable Context context, @Nullable String dbName, @Nullable SQLiteDatabase.CursorFactory factory,
                            int version, @Nullable DatabaseErrorHandler errorHandler, List<String> createSqlList) {
        super(context, dbName, factory, version, errorHandler);
        createTableList = new ArrayList<>();
        createTableList.addAll(createSqlList);
    }

    /**
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "FMsg:onCreate() called with: db = [" + db.getPath() + "]");
        db.execSQL(SQL_CREATE_TABLE);

    }

    /**
     * 当数据库的版本有更新的时候回调函数
     *
     * @param db         数据库对象
     * @param oldVersion 数据库旧版本
     * @param newVersion 数据库新版本
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "FMsg:onUpgrade() called with: db = [" + db.getPath() + "], oldVersion = [" + oldVersion + "], newVersion = [" + newVersion + "]");
        // onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    /**
     * 当数据库打开的时候进行调用
     *
     * @param db
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        Log.d(TAG, "FMsg:onOpen() called with: db = [" + db.getPath() + "]");
        super.onOpen(db);
    }
}
