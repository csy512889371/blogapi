package com.cto.edu.blog.entity.blogCategory;


import com.cto.edu.common.entity.UUIDEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 主题类别
 */
@Entity
@Table(name = "EVA_BLOG_CATEGORY")
public class BlogCategory extends UUIDEntity<String> {

    /**
     * 类别名称: 云/大数据 系统/测试 前端 /后端 /版本控制
     */
    @Column(name = "NAME")
    private String name;

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

    public int getInino() {
        return inino;
    }

    public void setInino(int inino) {
        this.inino = inino;
    }
}
