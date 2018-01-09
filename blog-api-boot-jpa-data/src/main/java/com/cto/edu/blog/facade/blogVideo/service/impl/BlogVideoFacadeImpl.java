package com.cto.edu.blog.facade.blogVideo.service.impl;

import com.cto.edu.blog.core.blogVideo.service.BlogVideoService;
import com.cto.edu.blog.entity.blogVideo.BlogVideo;
import com.cto.edu.blog.facade.blogVideo.service.BlogVideoFacade;
import com.cto.edu.common.model.search.Searchable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("blogVideoFacadeImpl")
public class BlogVideoFacadeImpl implements BlogVideoFacade {

    @Autowired
    private BlogVideoService blogVideoService;

    @Override
    public Page<BlogVideo> findForPageByTopicId(String topicId, Pageable page) {
        return blogVideoService.findForPageByTopicId(topicId, page);
    }

    @Override
    public Page<BlogVideo> findAllForPage(Pageable page) {
        return blogVideoService.findAllForPage(page);
    }

    @Override
    public Page<BlogVideo> listPage(Searchable searchable) {
        return blogVideoService.findAll(searchable);
    }

    @Override
    public BlogVideo getById(String videoId) {
        return blogVideoService.getById(videoId);
    }

    @Override
    public Page<BlogVideo> findForPageByTopicName(String name, Pageable page) {
        return blogVideoService.findForPageByTopicName(name, page);
    }

    @Override
    public void addOneViewCount(String videoId) {
        blogVideoService.addOneViewCount(videoId);
    }

}
