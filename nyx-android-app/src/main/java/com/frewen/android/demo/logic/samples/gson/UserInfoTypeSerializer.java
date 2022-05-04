package com.frewen.android.demo.logic.samples.gson;

import com.frewen.android.demo.logic.model.UserInfo;
import com.frewen.android.demo.logic.model.UserInfo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * @filename: UserinfoTypeSerializer
 * @introduction: 可定制序列化器
 * @author: Frewen.Wong
 * @time: 2020/8/2 08:31
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class UserInfoTypeSerializer implements JsonSerializer<UserInfo> {

    @Override
    public JsonElement serialize(UserInfo user, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("user_name", user.getName());
        obj.addProperty("age", user.getAge());
        return obj;
    }
}
