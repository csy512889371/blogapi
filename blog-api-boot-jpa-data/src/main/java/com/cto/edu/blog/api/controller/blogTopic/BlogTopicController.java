package com.cto.edu.blog.api.controller.blogTopic;

import com.alibaba.fastjson.JSONObject;
import com.cto.edu.blog.entity.blogTopic.BlogTopic;
import com.cto.edu.blog.facade.blogTopic.service.BlogTopicFacade;
import com.cto.edu.common.model.search.Searchable;
import com.cto.edu.common.vo.ViewerResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/blog/topic")
public class BlogTopicController {

    private static final Logger LOG = LoggerFactory.getLogger(BlogTopicController.class);

    @Resource
    private BlogTopicFacade blogTopicFacade;

    @SuppressWarnings("unchecked")
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
                Page<BlogTopic> topicPage =  blogTopicFacade.listPage(searchable);
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
