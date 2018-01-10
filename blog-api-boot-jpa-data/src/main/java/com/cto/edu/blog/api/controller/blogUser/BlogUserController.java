package com.cto.edu.blog.api.controller.blogUser;

import com.cto.edu.blog.api.vo.UserVO;
import com.cto.edu.blog.entity.user.UmsPerson;
import com.cto.edu.blog.entity.user.UmsUser;
import com.cto.edu.blog.facade.user.exception.UserException;
import com.cto.edu.blog.facade.user.service.UmsUserFacade;
import com.cto.edu.common.vo.ViewerResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(value = "user", description = "用户")
@RestController
@RequestMapping("/api/blog/user")
public class BlogUserController {

    @Resource
    private UmsUserFacade umsUserFacade;

    @ApiOperation(value="创建用户", notes="根据用户对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "UmsUser")
    @PostMapping(value = "/add")
    public ViewerResult add(@RequestBody UmsUser user) {
        ViewerResult result = new ViewerResult();
        try {

            UmsPerson person = new UmsPerson();
            person.setName(user.getNickname());
            person.setSn(user.getUsername());
            user.setPerson(person);
            user = umsUserFacade.register(user);
            UserVO userVO = new UserVO();
            userVO.convertPOToVO(user);
            result.setSuccess(true);
            result.setData(userVO);
        } catch (UserException e) {
            result.setSuccess(false);
            if ("user.person.sn.exists".equals(e.getCode())) {
                result.setErrMessage("邮箱号已存在");
            } else {
                result.setErrMessage(e.getMessage());
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrMessage(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

}
