package com.frewen.android.demo.business.samples.sqlite;

import static com.frewen.android.demo.business.samples.sqlite.BaseColumns.ID;
import static com.frewen.android.demo.business.samples.sqlite.UserColumns.TABLE_NAME;
import static com.frewen.android.demo.business.samples.sqlite.UserColumns.USER_AGE;
import static com.frewen.android.demo.business.samples.sqlite.UserColumns.USER_CONTENT;
import static com.frewen.android.demo.business.samples.sqlite.UserColumns.USER_CREATE_DATE;
import static com.frewen.android.demo.business.samples.sqlite.UserColumns.USER_NAME;
import static com.frewen.android.demo.business.samples.sqlite.UserColumns.USER_TELEPHONE;

/**
 * @filename: DBConstant
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/6/17 23:25
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class SQLiteConstant {

    public static final String DB_NAME = "aura_db.db";
    public static final int DB_VERSION = 2;
    public static final String DB_TABLE_NAME = "User";

    /**
     * create table 表名(字段名称  数据类型  约束,字段名称  数据类型  约束,字段名称  数据类型  约束,.....)
     * 数据类型：
     * INTEGER: 表示整型;
     * real: 表示浮点型;
     * TEXT: 表示文本型;
     * blob:表示二进制类型
     * 约束名称：
     * primary key: 表示主键;
     * autoincrement: 表示自增长
     */
    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + ID + " INTEGER PRIMARY KEY,"
            + USER_NAME + " VARCHAR(50),"
            + USER_AGE + " INTEGER,"
            + USER_TELEPHONE + " VARCHAR(11),"
            + USER_CONTENT + " TEXT,"
            + USER_CREATE_DATE + " INTEGER"
            + ");";

}
