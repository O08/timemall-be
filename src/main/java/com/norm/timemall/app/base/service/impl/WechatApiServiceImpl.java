package com.norm.timemall.app.base.service.impl;

import cn.hutool.core.util.CharsetUtil;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.norm.timemall.app.base.config.WechatConfig;
import com.norm.timemall.app.base.entity.Account;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.pojo.FetchWechatAccessTokenBO;
import com.norm.timemall.app.base.pojo.FetchWechatUserInfoBO;
import com.norm.timemall.app.base.pojo.WechatAccessTokenResponse;
import com.norm.timemall.app.base.service.WechatApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WechatApiServiceImpl implements WechatApiService {
    @Autowired
    private WechatConfig wechatConfig;

    @Override
    public FetchWechatAccessTokenBO fetchAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> uriVariables = new LinkedMultiValueMap<>();

        uriVariables.add("appid",wechatConfig.getWebappId());
        uriVariables.add("secret",wechatConfig.getWebappSecret());
        uriVariables.add("code",code);
        uriVariables.add("grant_type","authorization_code");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(wechatConfig.getTokenUri());
        URI uri = builder.queryParams(uriVariables).build().encode().toUri();

        String wechatAccessTokenResponse = restTemplate.getForObject(uri, String.class);
        log.info(wechatAccessTokenResponse);
        try {

            WechatAccessTokenResponse responseObj = new ObjectMapper().readValue(wechatAccessTokenResponse, WechatAccessTokenResponse.class);
            if(responseObj==null){
                throw new ErrorCodeException(CodeEnum.FAILED);
            }
            FetchWechatAccessTokenBO bo = new FetchWechatAccessTokenBO();
            bo.setAccessToken(responseObj.getAccess_token());
            bo.setExpiresIn(responseObj.getExpires_in());
            bo.setRefreshToken(responseObj.getRefresh_token());
            bo.setOpenid(responseObj.getOpenid());
            bo.setScope(responseObj.getScope());
            bo.setUnionid(responseObj.getUnionid());
            return bo;

        } catch (JsonProcessingException e) {
            throw new ErrorCodeException(CodeEnum.FAILED);
        }

    }

    @Override
    public FetchWechatUserInfoBO fetchUserInfo(String accessToken, String openid) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new FastJsonHttpMessageConverter());
        MultiValueMap<String, String> uriVariables = new LinkedMultiValueMap<>();

        uriVariables.add("access_token",accessToken);
        uriVariables.add("openid",openid);


        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(wechatConfig.getUserInfoUri());
        URI uri = builder.queryParams(uriVariables).build().encode().toUri();

        String wechatUserInfoResponse = restTemplate.getForObject(uri, String.class);

        log.info(wechatUserInfoResponse);
        FetchWechatUserInfoBO bo = new Gson().fromJson(wechatUserInfoResponse, FetchWechatUserInfoBO.class);

       // 处理nickname 乱码问题
        String nickName = CharsetUtil.convert(bo.getNickname(), StandardCharsets.ISO_8859_1, StandardCharsets.UTF_8);
        bo.setNickname(nickName);


        return bo;

    }
}
