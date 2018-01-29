package com.cto.edu.blog.entity.blogVideo;

import com.cto.edu.blog.entity.blogVideo.enums.EssenceEnum;
import com.cto.edu.blog.entity.blogVideo.enums.SoloEnum;
import com.cto.edu.common.entity.UUIDEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "EVA_BLOG_VIDEO")
public class BlogVideo extends UUIDEntity<String> implements Serializable {

    /**
     * 所属主题ID
     */
    @Column(name = "TOPIC_ID")
    private String topicId;

    /**
     * 所属主题名称
     */
    @Column(name = "TOPIC_NAME")
    private String topicName;

    /**
     * 视频名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 图片URL
     */
    @Column(name = "IMAGE_URL")
    private String imageUrl;

    /**
     * 价格
     */
    @Column(name = "PRICE")
    private int price;

    /**
     * 购买地址
     */
    @Column(name = "BUY_URL")
    private String buyUrl;

    /**
     * 视频内容
     */
    @Column(name = "CONTENT")
    private String content;

    /**
     * 是否精华
     */
    @Column(name = "IS_ESSENCE")
    private Short isEssence = EssenceEnum.TRUE.getValue();

    /**
     * 是否单销
     */
    @Column(name = "IS_SOLO")
    private Short isSolo = SoloEnum.FALSE.getValue();

    /**
     * 发布时间
     */
    @Column(name = "PUBLIC_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publicTime;

    /**
     * 收藏数量
     */
    @Column(name = "COLLECTION")
    private int collection;

    /**
     * 好评数量
     */
    @Column(name = "PRAISE")
    private int praise;

    /**
     * 查看数量
     */
    @Column(name = "VIEW")
    private int view;

    /**
     * 评论数量
     */
    @Column(name = "COMMENT")
    private int comment;

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBuyUrl() {
        return buyUrl;
    }

    public void setBuyUrl(String buyUrl) {
        this.buyUrl = buyUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Short getIsEssence() {
        return isEssence;
    }

    public void setIsEssence(Short isEssence) {
        this.isEssence = isEssence;
    }

    public Short getIsSolo() {
        return isSolo;
    }

    public void setIsSolo(Short isSolo) {
        this.isSolo = isSolo;
    }

    public Date getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(Date publicTime) {
        this.publicTime = publicTime;
    }

    public int getCollection() {
        return collection;
    }

    public void setCollection(int collection) {
        this.collection = collection;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }
}
