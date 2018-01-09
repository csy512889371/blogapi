package com.cto.edu.blog.api.controller.blogCategory;

import com.cto.edu.blog.facade.blogCategory.service.BlogCategoryFacade;
import com.cto.edu.common.vo.ViewerResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/blog/category")
public class BlogCategoryController {

    private static final Logger LOG = LoggerFactory.getLogger(BlogCategoryController.class);

    @Resource
    private BlogCategoryFacade blogCategoryFacade;

    @PostMapping(value = "/findAll")
    public ViewerResult findAll() {
        ViewerResult result = new ViewerResult();
        try {
            result.setData(blogCategoryFacade.findAll());
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrMessage("查找主题类别失败");
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

}
