package com.norm.timemall.app.base.pojo;

import lombok.Data;

@Data
public class WechatAccessTokenResponse {
    private String access_token;
    private String expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;
}
