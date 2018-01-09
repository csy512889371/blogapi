package com.cto.edu.blog.entity.blogBook;


import com.cto.edu.common.entity.UUIDEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 书籍
 */
@Entity
@Table(name = "EVA_BLOG_BOOK")
public class BlogBook extends UUIDEntity<String> {

    /**
     * 书籍名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 书籍内容
     */
    @Column(name = "CONTENT")
    private String content;

    /**
     * 书籍数量
     */
    @Column(name = "COLLECTION")
    private int collection;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCollection() {
        return collection;
    }

    public void setCollection(int collection) {
        this.collection = collection;
    }
}
