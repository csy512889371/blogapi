package com.cto.edu.blog.core.blogCategory.service;

import com.cto.edu.blog.core.blogCategory.repository.BlogCategoryRepository;
import com.cto.edu.blog.entity.blogCategory.BlogCategory;
import com.cto.edu.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogCategoryService extends BaseService<BlogCategory, String> {

    @Autowired
    private BlogCategoryRepository blogCategoryRepository;

}
