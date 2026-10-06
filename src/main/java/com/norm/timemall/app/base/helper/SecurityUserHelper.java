package com.norm.timemall.app.base.helper;

import com.norm.timemall.app.base.security.CustomizeUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 用户辅助类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUserHelper {
    /**
     * 获取当前用户
     * @return
     */
    public static Authentication getCurrentUserAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取当前用户
     * @return
     */
    public static CustomizeUser getCurrentPrincipal(){
        return (CustomizeUser) getCurrentUserAuthentication().getPrincipal();
    }
    /**
     * user 是否登录
     * @return
     */
    public static boolean alreadyLogin(){
        return !(SecurityUserHelper.getCurrentUserAuthentication()  instanceof AnonymousAuthenticationToken);
    }

    /**
     * 登出
     * @return
     */
    public static void logout(){
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
    }


}
