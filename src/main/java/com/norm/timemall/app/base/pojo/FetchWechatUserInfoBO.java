package com.norm.timemall.app.base.pojo;

import lombok.Data;

@Data
public class FetchWechatUserInfoBO {

    private String openid;
    private String nickname;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private String unionid;

}
