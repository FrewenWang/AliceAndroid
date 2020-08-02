package com.frewen.android.demo.bean;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;

/**
 * @filename: User
 * @introduction: 上面的注解是lombok的注解，起到简化Bean类的作用。
 * 下面的注解是SerializedName
 * @author: Frewen.Wong
 * @time: 2020/6/21 17:45
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@Since(1.0)
public class UserInfo {
    //Gson在序列化的时: 如果服务端使用的字段名称和我们想使用的字段名称不一样
    @SerializedName(value = "user_name")
    private String name;

    private int age;

    @Since(1.1)
    private String gender;

    public UserInfo(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
