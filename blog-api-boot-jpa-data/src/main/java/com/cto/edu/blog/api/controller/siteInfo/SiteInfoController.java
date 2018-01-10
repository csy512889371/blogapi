package com.cto.edu.blog.api.controller.siteInfo;

import com.cto.edu.common.model.SiteInfoProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Api(value = "siteInfo", description = "站点信息")
@RestController
@RequestMapping("/api/siteInfo")
public class SiteInfoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SiteInfoController.class);

    @Value("${siteVersion}")
    private String siteVersion;

    @Autowired
    private SiteInfoProperties siteInfoProperties;

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

}
