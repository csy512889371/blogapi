package com.cto.edu.blog.api.controller.blogVideo;

import com.alibaba.fastjson.JSONObject;
import com.cto.edu.blog.entity.blogVideo.BlogVideo;
import com.cto.edu.blog.facade.blogVideo.service.BlogVideoFacade;
import com.cto.edu.common.model.search.Searchable;
import com.cto.edu.common.vo.ViewerResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(value = "video", description = "视频信息")
@RestController
@RequestMapping("/api/blog/video")
public class BlogVideoController {
    private static final Logger LOG = LoggerFactory.getLogger(BlogVideoController.class);

    @Resource
    private BlogVideoFacade blogVideoFacade;

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "分页查询视频", notes = "根据主题id分页查询主题下的视频信息")
    @ApiImplicitParam(name = "obj", value = "{\"topicId\":\"3\",\"number\":0,\"size\":1}", required = true, dataType = "JSONObject")
    @PostMapping(value = "/findForPageByTopic")
    public ViewerResult findForPageByTopic(@RequestBody JSONObject obj) {
        ViewerResult result = new ViewerResult();
        try {
            String topicId = (String) obj.get("topicId");
            int number = obj.getInteger("number");
            int size = obj.getInteger("size");
            Pageable page = PageRequest.of(number, size);
            Searchable searchable = Searchable.newSearchable();
            searchable.setPage(page);

            if (StringUtils.isEmpty(topicId)) {
                Page<BlogVideo> topicPage = blogVideoFacade.listPage(searchable);
                result.setData(topicPage);
            } else {
                Page<BlogVideo> topicList = blogVideoFacade.findForPageByTopicId(topicId, page);
                result.setData(topicList);
            }

            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrMessage("查找视频失败");
            LOG.error(e.getMessage(), e);
        }
        return result;
    }


    @ApiOperation(value = "分页查询视频", notes = "分页查询主题下的视频信息")
    @ApiImplicitParam(name = "obj", value = "{\"number\":0,\"size\":1}", required = true, dataType = "JSONObject")
    @PostMapping(value = "/findAllForPage")
    public ViewerResult findAllForPage(@RequestBody JSONObject obj) {
        ViewerResult result = new ViewerResult();
        try {
            int number = obj.getInteger("number");
            int size = obj.getInteger("size");
            Pageable page = PageRequest.of(number, size);
            Page<BlogVideo> topicPage = blogVideoFacade.findAllForPage(page);
            result.setData(topicPage);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrMessage("查找视频失败");
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @ApiOperation(value = "分页查询视频", notes = "根据视频名称分页查询主题下的视频信息")
    @ApiImplicitParam(name = "obj", value = "{\"name\":\"js\",\"number\":0,\"size\":1}", required = true, dataType = "JSONObject")
    @SuppressWarnings("unchecked")
    @PostMapping(value = "/findForPageByName")
    public ViewerResult findForPageByName(@RequestBody JSONObject obj) {
        ViewerResult result = new ViewerResult();
        try {
            String name = (String) obj.get("name");
            int number = obj.getInteger("number");
            int size = obj.getInteger("size");
            Pageable page = PageRequest.of(number, size);
            Searchable searchable = Searchable.newSearchable();
            searchable.setPage(page);

            if (StringUtils.isNotEmpty(name)) {
                Page<BlogVideo> topicList = blogVideoFacade.findForPageByTopicName(name, page);
                result.setData(topicList);
            }
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrMessage("根据名称查找视频失败");
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @ApiOperation(value = "更新视频查看次数", notes = "根据视频的id将视频的查看次数加1")
    @ApiImplicitParam(name = "obj", value = "{\"videoId\":\"1\"}", required = true, dataType = "JSONObject")
    @SuppressWarnings("unchecked")
    @PostMapping(value = "/viewVideo")
    public ViewerResult viewVideo(@RequestBody JSONObject obj) {
        ViewerResult result = new ViewerResult();
        try {
            String videoId = (String) obj.get("videoId");
            blogVideoFacade.addOneViewCount(videoId);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrMessage("更新查看次数失败");
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @ApiOperation(value = "查询视频信息", notes = "根据视频的id查询视频信息")
    @ApiImplicitParam(name = "obj", value = "{\"id\":\"1\"}", required = true, dataType = "JSONObject")
    @PostMapping(value = "/findById")
    public ViewerResult findById(@RequestBody JSONObject obj) {
        ViewerResult result = new ViewerResult();
        BlogVideo video = null;
        try {
            String id = obj.getString("id");
            video = blogVideoFacade.getById(id);
            result.setSuccess(true);
            result.setData(video);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrMessage(e.getMessage());
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

}
