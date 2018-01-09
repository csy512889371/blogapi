package com.cto.edu.blog.facade.blogCategory.service.impl;

import com.cto.edu.blog.core.blogCategory.service.BlogCategoryService;
import com.cto.edu.blog.entity.blogCategory.BlogCategory;
import com.cto.edu.blog.facade.blogCategory.service.BlogCategoryFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("blogCategoryFacadeImpl")
public class BlogCategoryFacadeImpl implements BlogCategoryFacade {

    @Autowired
    private BlogCategoryService blogCategoryService;

    @Override
    public List<BlogCategory> findAll() {
        return blogCategoryService.findAll();
    }
}
