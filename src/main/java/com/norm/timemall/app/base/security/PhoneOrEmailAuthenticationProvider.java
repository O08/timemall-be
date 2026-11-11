package com.norm.timemall.app.base.security;



import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.base.service.WechatApiService;
import com.norm.timemall.app.base.util.mate.MybatisMateEncryptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PhoneOrEmailAuthenticationProvider implements AuthenticationProvider {


    @Autowired
    private WechatApiService wechatApiService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private MybatisMateEncryptor mybatisMateEncryptor;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        PhoneOrEmailAuthenticationToken token = (PhoneOrEmailAuthenticationToken) authentication;


        // ，从数据库中读取对应的用户信息
        CustomizeUser loadedUser = (CustomizeUser) accountService.loadUserForPhoneOrEmailLogin(token.getUsername());

        if (loadedUser == null) {
          throw new InternalAuthenticationServiceException("user name not found");
        }

        if(! new BCryptPasswordEncoder().matches(token.getPassword(),loadedUser.getPassword())){
            throw new BadCredentialsException("Invalid Credentials");
        }

        return new PhoneOrEmailAuthenticationToken(loadedUser, null,loadedUser.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PhoneOrEmailAuthenticationToken.class.isAssignableFrom(authentication);
    }


}

