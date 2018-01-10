package com.cto.edu.blog.api.controller.blogUser;

import com.alibaba.fastjson.JSONObject;
import com.cto.edu.blog.api.vo.LoginUserVO;
import com.cto.edu.blog.entity.log.UmsLog;
import com.cto.edu.blog.entity.log.enums.LogLevelEnum;
import com.cto.edu.blog.entity.log.enums.LogTypeEnum;
import com.cto.edu.blog.entity.log.enums.OpResultEnum;
import com.cto.edu.blog.entity.user.UmsUser;
import com.cto.edu.blog.facade.jwt.service.UmsJwtFacade;
import com.cto.edu.blog.facade.log.service.UmsLogFacade;
import com.cto.edu.blog.facade.user.service.UmsUserFacade;
import com.cto.edu.common.utils.DateUtil;
import com.cto.edu.common.utils.NetworkUtil;
import com.cto.edu.common.vo.ViewerResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;


@Api(value = "userLogin", description = "用户登录")
@RestController
@RequestMapping("/api/user/login")
public class UserLoginController {

    @Resource
    private UmsUserFacade umsUserFacade;

    @Resource
    private UmsJwtFacade umsJwtFacade;

    @Resource
    private UmsLogFacade umsLogFacade;

    @ApiOperation(value = "登录", notes = "根据用户名、密码登录")
    @ApiImplicitParam(name = "obj", value = "{\"appSn\":\"\",\"username\":\"\",\"password\":\"\",\"remember\":true}", required = true, dataType = "JSONObject")
    @PostMapping(value = "")
    public ViewerResult login(@RequestBody JSONObject obj, HttpServletRequest request) {
        ViewerResult result = new ViewerResult();
        UmsUser user = null;
        String appSn = null;
        String username = null;
        String password = null;
        boolean remember = false;
        String jwt = null;
        LocalDateTime opTime = DateUtil.utilDateToLocalDateTime(new Date());
        long startTime = System.currentTimeMillis();
        try {
            appSn = obj.getString("appSn");
            username = obj.getString("username");
            password = obj.getString("password");
            remember = obj.getBooleanValue("remember");
            user = umsUserFacade.login(username, password);
            LoginUserVO userVO = new LoginUserVO();
            userVO.convertPOToVO(user);
            jwt = umsJwtFacade.createJwt(user.getUsername(), remember);
            userVO.setToken(jwt);
            result.setSuccess(true);
            result.setData(userVO);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrMessage(e.getMessage());
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        try {
            log(appSn, username, remember, endTime - startTime, opTime, result.isSuccess(), request);
        } catch (Exception e) {
        }
        return result;
    }

    private void log(String appSn, String username, boolean remember,
                     long execTime, LocalDateTime opTime, boolean success, HttpServletRequest request) {
        try {
            UmsLog umsLog = new UmsLog();
            umsLog.setAppSn(appSn);
            umsLog.setBackEndAccessPath("/api/user/login");
            umsLog.setBrowser(NetworkUtil.getBrowser(request));
            umsLog.setExecTime(execTime);
            umsLog.setIp(NetworkUtil.getIpAddress(request));
            umsLog.setOpResource("用户登录");
            umsLog.setLogLevel(LogLevelEnum.NORMAL.getValue());
            umsLog.setLogType(LogTypeEnum.LOGIN.getValue());
            String desc = "用户登录：用户名【" + username + "】、记住密码【" + (remember ? "是" : "否") + "】";
            umsLog.setOpDesc(desc);
            umsLog.setOpResult(success ? OpResultEnum.SUCCESS.getValue() : OpResultEnum.FAIl.getValue());
            umsLog.setOpSystem(NetworkUtil.getOS(request));
            umsLog.setOpTime(opTime);
            umsLog.setUsername(username);
            umsLogFacade.save(umsLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}