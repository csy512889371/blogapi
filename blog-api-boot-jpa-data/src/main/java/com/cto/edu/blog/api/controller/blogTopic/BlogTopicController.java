package com.cto.edu.blog.api.controller.blogTopic;

import com.alibaba.fastjson.JSONObject;
import com.cto.edu.blog.entity.blogTopic.BlogTopic;
import com.cto.edu.blog.facade.blogTopic.service.BlogTopicFacade;
import com.cto.edu.common.model.search.Searchable;
import com.cto.edu.common.vo.ViewerResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "topic", description = "主题")
@RestController
@RequestMapping("/api/blog/topic")
public class BlogTopicController {

    private static final Logger LOG = LoggerFactory.getLogger(BlogTopicController.class);

    @Resource
    private BlogTopicFacade blogTopicFacade;

    @ApiOperation(value = "分页获取主题列表", notes = "根据类别获取类别下的主题列表")
    @ApiImplicitParam(name = "obj", value = "{\"categoryIds\":[],\"number\":0,\"size\":1}", required = true, dataType = "JSONObject")
    @PostMapping(value = "/findForPage")
    public ViewerResult findForPage(@RequestBody JSONObject obj) {

        ViewerResult result = new ViewerResult();
        try {
            List<String> categoryIds = (List<String>) obj.get("categoryIds");
            int number = obj.getInteger("number");
            int size = obj.getInteger("size");
            Pageable page = PageRequest.of(number, size);
            Searchable searchable = Searchable.newSearchable();
            searchable.setPage(page);

            if (categoryIds == null || categoryIds.isEmpty()) {
                Page<BlogTopic> topicPage = blogTopicFacade.listPage(searchable);
                result.setData(topicPage);
            } else {
                Page<BlogTopic> topicList = blogTopicFacade.findTopicByCategoryIds(categoryIds, page);
                result.setData(topicList);
            }

            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrMessage("查找主题失败");
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @ApiOperation(value = "获取主题信息", notes = "根据Id获取主题信息")
    @ApiImplicitParam(name = "obj", value = "{\"id\":\"1\"}", required = true, dataType = "JSONObject")
    @PostMapping(value = "/findById")
    public ViewerResult findById(@RequestBody JSONObject obj) {
        ViewerResult result = new ViewerResult();
        BlogTopic topic = null;
        try {
            String id = obj.getString("id");
            topic = blogTopicFacade.getById(id);
            result.setSuccess(true);
            result.setData(topic);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrMessage(e.getMessage());
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

}
