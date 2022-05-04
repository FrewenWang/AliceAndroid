package com.frewen.android.demo.logic.model;

import java.io.Serializable;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;

/**
 * @filename: Comment
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/7 14:03
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class Comment extends BaseObservable implements Serializable {
    /**
     * id : 784
     * itemId : 6739143063064549000
     * commentId : 6739212214408380000
     * userId : 65200808093
     * commentType : 1
     * createTime : 1569095152
     * commentCount : 4454
     * likeCount : 152
     * commentText : 看见没。比甜蜜暴击好看一万倍！
     * imageUrl : null
     * videoUrl : null
     * width : 0
     * height : 0
     * hasLiked : false
     * author : {"id":978,"userId":65200808093,"name":"带鱼裹上面包糠","avatar":"https://sf1-nhcdn-tos.pstatp.com/obj/tos-cn-i-0000/9041325b8fd44dd09fd41d5f2bd379bd","description":null,"likeCount":0,"topCommentCount":0,"followCount":0,"followerCount":0,"qqOpenId":null,"expires_time":0,"score":0,"historyCount":0,"commentCount":0,"favoriteCount":0,"feedCount":0,"hasFollow":false}
     * ugc : {"likeCount":153,"shareCount":0,"commentCount":4454,"hasFavorite":false,"hasLiked":true}
     */
    public int id;
    public long itemId;
    public long commentId;
    public long userId;
    public int commentType;
    public long createTime;
    public int commentCount;
    public int likeCount;
    public String commentText;
    public String imageUrl;
    public String videoUrl;
    public int width;
    public int height;
    public boolean hasLiked;
    public UserInfo author;
    /**
     * 用户生成数据：点赞数、评论数、转发数等等
     */
    public Ugc ugc;

    public Ugc getUgc() {
        if (ugc == null) {
            ugc = new Ugc();
        }
        return ugc;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null || !(obj instanceof Comment)) {
            return false;
        }
        Comment newComment = (Comment) obj;
        return likeCount == newComment.likeCount
                && hasLiked == newComment.hasLiked
                && (author != null && author.equals(newComment.author))
                && (ugc != null && ugc.equals(newComment.ugc));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getCommentType() {
        return commentType;
    }

    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isHasLiked() {
        return hasLiked;
    }

    public void setHasLiked(boolean hasLiked) {
        this.hasLiked = hasLiked;
    }

    public UserInfo getAuthor() {
        return author;
    }

    public void setAuthor(UserInfo author) {
        this.author = author;
    }

    public void setUgc(Ugc ugc) {
        this.ugc = ugc;
    }
}
