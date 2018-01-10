package com.cto.edu.blog.api.controller.blogCategory;

import com.cto.edu.blog.facade.blogCategory.service.BlogCategoryFacade;
import com.cto.edu.common.vo.ViewerResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(value = "category", description = "主题类别")
@RestController
@RequestMapping("/api/blog/category")
public class BlogCategoryController {

    private static final Logger LOG = LoggerFactory.getLogger(BlogCategoryController.class);

    @Resource
    private BlogCategoryFacade blogCategoryFacade;

    @ApiOperation(value="获取主题分类列表", notes="")
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
