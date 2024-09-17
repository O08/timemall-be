package com.norm.timemall.app.base.pojo;

import lombok.Data;

@Data
public class FetchWechatAccessTokenBO {

    private String accessToken;
    private String expiresIn;
    private String refreshToken;
    private String openid;
    private String scope;
    private String unionid;
}
