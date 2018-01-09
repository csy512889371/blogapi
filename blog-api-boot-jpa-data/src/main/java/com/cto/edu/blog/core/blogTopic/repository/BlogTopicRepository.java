package com.cto.edu.blog.core.blogTopic.repository;

import com.cto.edu.blog.entity.blogTopic.BlogTopic;
import com.cto.edu.common.repository.CustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogTopicRepository extends CustomRepository<BlogTopic, String> {

    @Query("select a from BlogTopic a where a.categoryId=?1 order by a.inino asc ")
    List<BlogTopic> findTopicByCategoryId(String categoryId);

    @Query("select a from BlogTopic a where a.categoryId in ?1 order by a.inino asc")
    Page<BlogTopic> findTopicByCategoryIds(List<String> categoryIds, Pageable page);

    @Query("select a from BlogTopic a where a.id=?1 ")
    BlogTopic findByTopicId(String id);
}
