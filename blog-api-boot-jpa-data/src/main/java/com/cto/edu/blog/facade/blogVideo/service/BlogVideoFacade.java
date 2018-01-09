package com.cto.edu.blog.facade.blogVideo.service;

import com.cto.edu.blog.entity.blogVideo.BlogVideo;
import com.cto.edu.common.model.search.Searchable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogVideoFacade {

    /**
     * 根据主题Id 分页查询视频信息
     *
     * @param topicId 主题
     * @param page    分页参数
     * @return 视频列表
     */
    Page<BlogVideo> findForPageByTopicId(String topicId, Pageable page);

    /**
     * 分页查询
     * @param searchable 分页信息
     * @return 视频列表
     */
    Page<BlogVideo> listPage(Searchable searchable);

    /**
     * 根据Id查询组实体信息
     *
     * @param videoId 视频Id
     */
    BlogVideo getById(String videoId);

    /**
     * 根据名称模糊查询 视频
     * @param name 名称
     * @param page 分页信息
     * @return 视频列表
     */
    Page<BlogVideo> findForPageByTopicName(String name, Pageable page);


    /**
     * 增加一次查看视频的数量
     * @param videoId 视频Id
     */
    void addOneViewCount(String videoId);

    Page<BlogVideo> findAllForPage(Pageable page);

}
