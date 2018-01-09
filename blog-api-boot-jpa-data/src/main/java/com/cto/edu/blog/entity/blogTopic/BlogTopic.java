package com.cto.edu.blog.entity.blogTopic;


import com.cto.edu.common.entity.UUIDEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 文章主题
 */
@Entity
@Table(name = "EVA_BLOG_TOPIC")
public class BlogTopic extends UUIDEntity<String> {
    /**
     * 主题名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 图片url
     */
    @Column(name = "IMG_URL")
    private String imgUrl;

    /**
     * 购买地址
     */
    @Column(name = "BUY_URL")
    private String buyUrl;

    /**
     * 主题描述
     */
    @Column(name = "INFO")
    private String info;

    /**
     * 视频数量
     */
    @Column(name = "VIDEO_COUNT")
    private int videoCount;

    /**
     * 关注数量
     */
    @Column(name = "FOLLOW")
    private int follow;


    /**
     * 所属类别Id
     */
    @Column(name = "CATEGORY_ID")
    private String categoryId;

    /**
     * 所属类别名称
     */
    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    /**
     * 价格
     */
    @Column(name = "PRICE")
    private int price;

    /**
     * 排序
     */
    @Column(name = "ININO")
    private int inino;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getBuyUrl() {
        return buyUrl;
    }

    public void setBuyUrl(String buyUrl) {
        this.buyUrl = buyUrl;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }

    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getInino() {
        return inino;
    }

    public void setInino(int inino) {
        this.inino = inino;
    }
}
