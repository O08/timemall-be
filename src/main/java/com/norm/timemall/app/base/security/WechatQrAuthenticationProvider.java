package com.norm.timemall.app.base.security;


import com.norm.timemall.app.base.pojo.FetchWechatAccessTokenBO;
import com.norm.timemall.app.base.pojo.FetchWechatUserInfoBO;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.base.service.WechatApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WechatQrAuthenticationProvider implements AuthenticationProvider {


    @Autowired
    private WechatApiService wechatApiService;

    @Autowired
    private AccountService accountService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        WechatQrCodeAuthenticationToken token = (WechatQrCodeAuthenticationToken) authentication;
        String code = token.getCode();
        String state = token.getState();
        log.info("wechat login code：" + code);
        log.info("wechat login state：" + state);


        FetchWechatAccessTokenBO accessTokenBO = wechatApiService.fetchAccessToken(code);


        // ，从数据库中读取对应的用户信息  ob6e96HRKQ5Y_jcP2lIhtAK1wiUA
        CustomizeUser loadedUser = (CustomizeUser) accountService.loadUserForWechatLogin(accessTokenBO.getUnionid());

        if (loadedUser == null) {
           // create new
            FetchWechatUserInfoBO wechatUserInfo = wechatApiService.fetchUserInfo(accessTokenBO.getAccessToken(), accessTokenBO.getOpenid());
            accountService.doSignUpWithWechatUserInfo(wechatUserInfo);
            // load user info
            loadedUser = (CustomizeUser) accountService.loadUserForWechatLogin(accessTokenBO.getUnionid());
        }

        return new WechatQrCodeAuthenticationToken(loadedUser, null,loadedUser.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return WechatQrCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }


}

