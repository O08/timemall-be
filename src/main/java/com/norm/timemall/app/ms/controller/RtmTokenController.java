package com.norm.timemall.app.ms.controller;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.ms.builder.RtmTokenBuilder2;
import com.norm.timemall.app.ms.domain.vo.RtmTokenVO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RtmTokenController {
    private static String appId = "31ffac24a5494710bc8453e2c42e9d35";
    private static String appCertificate = "3c87808db5c449a083623c39983e8323";
    private static int expirationInSeconds = 3600;
    @ResponseBody
    @GetMapping(value = "/api/v1/ms/rtm/token")
    public RtmTokenVO getRtmToken(@AuthenticationPrincipal CustomizeUser user){
        RtmTokenBuilder2 token = new RtmTokenBuilder2();
        String result = token.buildToken(appId, appCertificate, user.getUserId(), expirationInSeconds);
        RtmTokenVO vo = new RtmTokenVO();
        vo.setToken(result);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
}
