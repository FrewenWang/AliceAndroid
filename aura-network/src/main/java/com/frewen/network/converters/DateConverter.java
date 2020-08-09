package com.frewen.network.converters;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * @filename: DateConverter
 * @introduction: 数据库存储的日期转换器
 * @author: Frewen.Wong
 * @time: 2020/8/9 10:38
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class DateConverter {

    @TypeConverter
    public static Long date2Long(Date date) {
        return date.getTime();
    }

    @TypeConverter
    public static Date long2Date(Long date) {
        return new Date(date);
    }

}
