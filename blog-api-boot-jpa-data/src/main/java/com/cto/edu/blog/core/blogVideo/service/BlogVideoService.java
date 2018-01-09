package com.cto.edu.blog.core.blogVideo.service;

import com.cto.edu.blog.core.blogVideo.repository.BlogVideoRepository;
import com.cto.edu.blog.entity.blogVideo.BlogVideo;
import com.cto.edu.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BlogVideoService extends BaseService<BlogVideo, String> {

    @Autowired
    private BlogVideoRepository blogVideoRepository;

    public Page<BlogVideo> findForPageByTopicId(String topicId, Pageable page) {
        return blogVideoRepository.findForPageByTopicId(topicId, page);
    }

    public Page<BlogVideo> findAllForPage(Pageable page) {
        return blogVideoRepository.findAllForPage(page);
    }


    public Page<BlogVideo> findForPageByTopicName(String name, Pageable page) {
        return blogVideoRepository.findForPageByTopicName(name, page);
    }

    public void addOneViewCount(String videoId) {
        blogVideoRepository.addOneViewCount(videoId);
    }

    public BlogVideo getById(String videoId) {
       return blogVideoRepository.findByVideoId(videoId);
    }
}
