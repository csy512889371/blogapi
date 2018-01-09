package com.cto.edu.blog.facade.blogTopic.service.impl;

import com.cto.edu.blog.core.blogTopic.service.BlogTopicService;
import com.cto.edu.blog.entity.blogTopic.BlogTopic;
import com.cto.edu.blog.facade.blogTopic.service.BlogTopicFacade;
import com.cto.edu.common.model.search.Searchable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("blogTopicFacadeImpl")
public class BlogTopicFacadeImpl implements BlogTopicFacade {

    @Autowired
    private BlogTopicService blogTopicService;


    @Override
    public List<BlogTopic> findTopicByCategoryId(String categoryId) {
        return blogTopicService.findTopicByCategoryId(categoryId);
    }

    @Override
    public Page<BlogTopic> findTopicByCategoryIds(List<String> categoryIds, Pageable page) {
        return blogTopicService.findTopicByCategoryIds(categoryIds, page);
    }

    @Override
    public List<BlogTopic> findAllTopic() {
        return blogTopicService.findAll();
    }

    @Override
    public Page<BlogTopic> listPage(Searchable searchable) {
        return blogTopicService.findAll(searchable);
    }

    @Override
    public BlogTopic getById(String topicId) {
        return blogTopicService.getById(topicId);
    }


}
