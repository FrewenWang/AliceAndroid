package com.frewen.android.demo.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;

import java.io.Serializable;

import androidx.annotation.Nullable;

/**
 * @filename: User
 * @introduction: 上面的注解是lombok的注解，起到简化Bean类的作用。
 *         下面的注解是SerializedName
 * @author: Frewen.Wong
 * @time: 2020/6/21 17:45
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@Since(1.0)
public class UserInfo implements Serializable {

    public int id;

    public long userId;
    //Gson在序列化的时: 如果服务端使用的字段名称和我们想使用的字段名称不一样
    @SerializedName(value = "user_name")
    private String name;
    public String avatar;
    public String description;
    private int age;

    @Since(1.1)
    private String gender;

    public int likeCount;
    public int topCommentCount;
    public int followCount;
    public int followerCount;
    public String qqOpenId;
    public long expires_time;
    public int score;
    public int historyCount;
    public int commentCount;
    public int favoriteCount;
    public int feedCount;
    public boolean hasFollow;

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
    public boolean equals(@Nullable Object obj) {
        if (obj == null || !(obj instanceof UserInfo))
            return false;
        UserInfo newUser = (UserInfo) obj;
        return TextUtils.equals(name, newUser.name)
                && TextUtils.equals(avatar, newUser.avatar)
                && TextUtils.equals(description, newUser.description)
                && likeCount == newUser.likeCount
                && topCommentCount == newUser.topCommentCount
                && followCount == newUser.followCount
                && followerCount == newUser.followerCount
                && qqOpenId == newUser.qqOpenId
                && expires_time == newUser.expires_time
                && score == newUser.score
                && historyCount == newUser.historyCount
                && commentCount == newUser.commentCount
                && favoriteCount == newUser.favoriteCount
                && feedCount == newUser.feedCount
                && hasFollow == newUser.hasFollow;
    }
}
