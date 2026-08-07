package com.norm.timemall.app.base.service;

import com.norm.timemall.app.base.pojo.FetchWechatAccessTokenBO;
import com.norm.timemall.app.base.pojo.FetchWechatUserInfoBO;
import org.springframework.stereotype.Service;

@Service
public interface WechatApiService {
    FetchWechatAccessTokenBO fetchAccessToken(String code);
    FetchWechatUserInfoBO fetchUserInfo(String accessToken, String openid);

}
