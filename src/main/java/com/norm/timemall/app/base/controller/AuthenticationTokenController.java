package com.norm.timemall.app.base.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationTokenController {
    @GetMapping("/api/v1/base/oauth/check_token")
    public SuccessVO checkToken() {
        // 能进到这里，说明 Spring Security 已经完成了 PAT 或 OAuth2 JWT 验签
        return new SuccessVO(CodeEnum.SUCCESS);
    }

}
