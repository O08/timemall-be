package com.norm.timemall.app.ms.controller;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.ms.builder.RtmResource;
import com.norm.timemall.app.ms.builder.RtmTokenBuilder2;
import com.norm.timemall.app.ms.domain.vo.RtmTokenVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RtmTokenController {
    @Autowired
    private RtmResource rtmResource;
    @ResponseBody
    @GetMapping(value = "/api/v1/ms/rtm/token")
    public RtmTokenVO getRtmToken(@AuthenticationPrincipal CustomizeUser user){
        RtmTokenBuilder2 token = new RtmTokenBuilder2();
        String result = token.buildToken(rtmResource.getAppId(), rtmResource.getAppCertificate(), user.getUserId(), rtmResource.getExpirationInSeconds());
        RtmTokenVO vo = new RtmTokenVO();
        vo.setToken(result);
        vo.setAppId(rtmResource.getAppId());
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
}
