package com.cto.edu.blog.core.blogTopic.service;

import com.cto.edu.blog.core.blogTopic.repository.BlogTopicRepository;
import com.cto.edu.blog.entity.blogTopic.BlogTopic;
import com.cto.edu.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogTopicService extends BaseService<BlogTopic, String> {


    @Autowired
    private BlogTopicRepository blogTopicRepository;

    public List<BlogTopic> findTopicByCategoryId(String categoryId) {
        return blogTopicRepository.findTopicByCategoryId(categoryId);
    }

    public Page<BlogTopic> findTopicByCategoryIds(List<String> categoryIds, Pageable page) {
        return blogTopicRepository.findTopicByCategoryIds(categoryIds, page);
    }

    public BlogTopic getById(String topicId) {
        return blogTopicRepository.findByTopicId(topicId);
    }
}
