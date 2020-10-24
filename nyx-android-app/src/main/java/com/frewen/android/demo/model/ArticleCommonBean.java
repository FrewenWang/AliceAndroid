package com.frewen.android.demo.model;

/**
 * @filename: ArticleCommonBean
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/8/3 19:21
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class ArticleCommonBean {
    /**
     * title : Candy Shop
     * author : Mohamed Chahin
     * imageUrl : https://resources.ninghao.org/images/candy-shop.jpg
     */
    private String title;
    private String author;
    private String imageUrl;

    /**
     * getAuthor
     * @return
     */
    public String getAuthor() {
        return author;
    }

    /**
     * setAuthor
     * @param author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * getTitle
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *setTitle
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * getImageUrl
     * @return
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * setImageUrl
     * @param imageUrl
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
