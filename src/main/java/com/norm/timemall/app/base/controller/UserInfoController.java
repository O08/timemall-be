package com.norm.timemall.app.base.controller;

import com.norm.timemall.app.base.entity.CurrentUser;
import com.norm.timemall.app.base.entity.UserInfoVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.security.CustomizeUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {
    /**
     * 获取登录用户信息
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/web_mall/me")
    public UserInfoVO retrieveUserInfo(){
      UserInfoVO user = new UserInfoVO();
        Authentication authentication = SecurityUserHelper.getCurrentUserAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken){
            user.setResponseCode(CodeEnum.USER_NOT_LOGIN);
        }else {
            CustomizeUser rawUser = (CustomizeUser)authentication.getPrincipal();
            CurrentUser currentUser = new CurrentUser();
            currentUser.setUserId(rawUser.getUserId()).setUsername(rawUser.getUsername());
            user.setUser(currentUser);
            user.setResponseCode(CodeEnum.SUCCESS);
        }
        return user;
    }
}
