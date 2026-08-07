package com.norm.timemall.app.base.controller;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.pojo.vo.BlvRtmTokenVO;
import com.norm.timemall.app.base.rtm.RtmJwtUtil;
import com.norm.timemall.app.base.security.CustomizeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RtmController {
    @Autowired
    private RtmJwtUtil rtmJwtUtil;
    @GetMapping(value = "/api/v1/base/rtm/token")
    public BlvRtmTokenVO getRtmToken(){
        CustomizeUser  user = SecurityUserHelper.getCurrentPrincipal();
        String token = rtmJwtUtil.createToken(user.getUserId());
        BlvRtmTokenVO vo = new BlvRtmTokenVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setToken(token);
        vo.setUserId(user.getUserId());
        vo.setUserName(user.getUsername());
        return vo;
    }
}
