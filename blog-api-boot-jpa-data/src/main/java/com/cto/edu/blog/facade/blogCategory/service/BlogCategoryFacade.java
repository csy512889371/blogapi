package com.cto.edu.blog.facade.blogCategory.service;


import com.cto.edu.blog.entity.blogCategory.BlogCategory;

import java.util.List;

public interface BlogCategoryFacade {

    List<BlogCategory> findAll();
}
