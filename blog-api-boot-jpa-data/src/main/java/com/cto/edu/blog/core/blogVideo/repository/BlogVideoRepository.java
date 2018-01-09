package com.cto.edu.blog.core.blogVideo.repository;

import com.cto.edu.blog.entity.blogVideo.BlogVideo;
import com.cto.edu.common.repository.CustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BlogVideoRepository extends CustomRepository<BlogVideo, String> {

    @Query("select a from BlogVideo a where a.topicId=?1 order by a.isSolo asc , a.isEssence asc , a.publicTime desc ")
    Page<BlogVideo> findForPageByTopicId(String topicId, Pageable page);

    @Query("select a from BlogVideo a order by a.isSolo asc , a.isEssence asc , a.publicTime desc  ")
    Page<BlogVideo> findAllForPage(Pageable page);

    @Query("select a from BlogVideo a where  a.name like %?1% order by a.isSolo asc , a.isEssence asc , a.publicTime desc ")
    Page<BlogVideo> findForPageByTopicName(String name, Pageable page);

    @Modifying
    @Query("update BlogVideo o set o.view= o.view + 1 where o.id=?1")
    void addOneViewCount(String videoId);

    @Query("select a from BlogVideo a where a.id=?1 ")
    BlogVideo findByVideoId(String id);

}
