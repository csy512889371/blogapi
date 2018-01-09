package com.cto.edu.blog.api.controller.blogUser;

import com.cto.edu.blog.api.vo.UserVO;
import com.cto.edu.blog.entity.user.UmsPerson;
import com.cto.edu.blog.entity.user.UmsUser;
import com.cto.edu.blog.facade.user.exception.UserException;
import com.cto.edu.blog.facade.user.service.UmsUserFacade;
import com.cto.edu.common.vo.ViewerResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/blog/user")
public class BlogUserController {

    @Resource
    private UmsUserFacade umsUserFacade;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
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
