package com.cto.edu.blog.api.controller.siteInfo;

import com.cto.edu.common.model.SiteInfoProperties;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Api(value = "siteInfo", description = "站点信息")
@RestController
@RequestMapping("/api/siteInfo")
public class SiteInfoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SiteInfoController.class);

    private static final String template = "mail/findPW.ftl";

    @Value("${siteVersion}")
    private String siteVersion;

    @Autowired
    private SiteInfoProperties siteInfoProperties;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @GetMapping(value = "/siteName")
    public String getSiteName() {
        return siteInfoProperties.getSiteName();
    }

    @GetMapping(value = {"/info", "/version"})
    public String getSiteInfo() {
        return siteInfoProperties.getSiteName() + "  " + siteVersion;
    }

    @GetMapping(value = "/say/{info}")
    public String say(@PathVariable("info") String info) {
        return "info: " + info;
    }

    @GetMapping(value = "/sayParam")
    @ApiOperation(value = "信息应答", notes = "返回基本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "info", value = "应答内容", dataType = "String")
    })
    public String sayParam(@RequestParam(value = "info", required = false, defaultValue = "架构师的成长之路") String myInfo) {
        return "info: " + myInfo;
    }

    @GetMapping(value = "sayGet")
    public String sayGet(@RequestParam(value = "info", required = false, defaultValue = "架构师的成长之路") String myInfo) {
        return "info: " + myInfo;
    }

    @GetMapping(value = "updateSysInfo")
    public String updateSysInfo() {
        LOGGER.debug("*** test debug log success ***");
        LOGGER.error("*** test error log success ***");
        return "success";
    }

    @GetMapping(value = "mailStr")
    public String mailStr() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("email", "123456@qq.com");
        map.put("newps", "123456");
        String text = "";
        try {
            text = getTextByTemplate(template, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    @RequestMapping(value = "/session")
    public String session(ModelMap map, HttpSession httpSession) {
        map.put("title", "第一个应用：sessionID=" + httpSession.getId());
        System.out.println("sessionID=" + httpSession.getId());
        return "index";
    }

    private String getTextByTemplate(String template, Map<String, Object> model) throws IOException, TemplateException {
        return FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfigurer.getConfiguration().getTemplate(template), model);
    }

}
