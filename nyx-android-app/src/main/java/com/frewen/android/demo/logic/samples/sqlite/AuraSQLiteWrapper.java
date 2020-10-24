package com.frewen.android.demo.logic.samples.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CancellationSignal;

import static com.frewen.android.demo.logic.samples.sqlite.SQLiteConstant.DB_NAME;
import static com.frewen.android.demo.logic.samples.sqlite.SQLiteConstant.DB_VERSION;

/**
 * @filename: AuraSQLiteProxy
 * @introduction: AuraSQLiteHelper的代理类，
 *         TODO 这个类还需要好好封装
 * @author: Frewen.Wong
 * @time: 2020/6/17 23:39
 * @copyright Copyright ©2019 Frewen.Wong. All Rights Reserved.
 */
public class AuraSQLiteWrapper {

    private AuraSQLiteHelper mSQLiteHelper;
    private SQLiteDatabase mDataBase;

    public AuraSQLiteWrapper(Context context) {
        mSQLiteHelper = new AuraSQLiteHelper(context, DB_NAME, null, DB_VERSION, null, null);
        mDataBase = mSQLiteHelper.getWritableDatabase();
    }

    /**
     * getReadableDatabase和getWritableDatabase 都是创建或者打开一些读写数据库
     * 如果数据库不存在则创建数据库，如果数据库存在则直接打开数据库，默认情况，这两个方法都表示
     * 创建和打开读写数据库（除非出现一些问题：例如磁盘空间已满，或者数据库的权限等）
     *
     * @param type
     */
    public void switchDataBase(DataBaseType type) {
        switch (type) {
            case Readable:
                mDataBase = mSQLiteHelper.getReadableDatabase();
            case Writable:
            default:
                mDataBase = mSQLiteHelper.getWritableDatabase();
        }
    }

    /**
     * 执行原始SQL脚本
     *
     * @param sql
     */
    public void execSQL(String sql) {
        mDataBase.execSQL(sql);
    }

    public void execSQL(String sql, Object[] bindArgs) {
        mDataBase.execSQL(sql, bindArgs);
    }

    /**
     * 数据库的插入
     * public long insert(String table, String nullColumnHack, ContentValues values)
     *
     * @param table          数据库表名
     * @param nullColumnHack 插入的时候，允许某些字段为空
     * @param values         插入表的内容(序列化对象)
     */
    public long insert(String table, String nullColumnHack, ContentValues values) {
        return mDataBase.insert(table, nullColumnHack, values);
    }

    /**
     * @param table       数据库表名
     * @param values      插入表的内容对象(序列化对象)
     * @param whereClause 当前修改的条件
     * @param whereArgs   修改条件的占位符
     */
    public long update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        return mDataBase.update(table, values, whereClause, whereArgs);
    }

    /**
     * @param table       数据库表名
     * @param whereClause 当前删除的条件
     * @param whereArgs   删除条件的占位符
     */
    public long delete(String table, String whereClause, String[] whereArgs) {
        return mDataBase.delete(table, whereClause, whereArgs);
    }

    public Cursor rawQuery(String sql, String[] selectionArgs,
                           CancellationSignal cancellationSignal) {
        return mDataBase.rawQuery(sql, selectionArgs, null);
    }


    public enum DataBaseType {
        Readable,
        Writable,
    }

}
