package com.frewen.android.demo.logic.samples.gson;

import com.frewen.android.demo.logic.model.User;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * @filename: UserinfoTypeSerializer
 * @introduction: 可定制序列化器
 * @author: Frewen.Wong
 * @time: 2020/8/2 08:31
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class UserInfoTypeDeserializer implements JsonDeserializer<User> {


    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        User user = new User(jsonObject.get("user_name").getAsString(), jsonObject.get("age").getAsInt());
        return user;
    }
}
