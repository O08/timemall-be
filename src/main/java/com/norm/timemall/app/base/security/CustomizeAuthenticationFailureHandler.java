package com.norm.timemall.app.base.security;

import com.google.gson.Gson;
import com.norm.timemall.app.base.entity.ErrorVO;
import com.norm.timemall.app.base.enums.Code;
import com.norm.timemall.app.base.enums.CodeEnum;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Hutengfei
 * @Description: 登录失败处理逻辑
 * @Date Create in 2019/9/3 15:52
 */
@Component
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //返回json数据
        Code code = null;
        if (e instanceof AccountExpiredException) {
            //账号过期
            code = CodeEnum.USER_ACCOUNT_EXPIRED;
        } else if (e instanceof BadCredentialsException) {
            //密码错误
            code = CodeEnum.USER_CREDENTIALS_ERROR;
        } else if (e instanceof CredentialsExpiredException) {
            //密码过期
            code = CodeEnum.USER_CREDENTIALS_EXPIRED;
        } else if (e instanceof DisabledException) {
            //账号不可用
            code = CodeEnum.USER_ACCOUNT_DISABLE;
        } else if (e instanceof LockedException) {
            //账号锁定
            code = CodeEnum.USER_ACCOUNT_LOCKED;
        } else if (e instanceof InternalAuthenticationServiceException) {
            //用户不存在
            code = CodeEnum.USER_ACCOUNT_NOT_EXIST;
        }else{
            //其他错误
            code = CodeEnum.FAILED;
        }
        Gson gson = new Gson();
        ErrorVO vo = new ErrorVO(code);
        //处理编码方式，防止中文乱码的情况
        httpServletResponse.setContentType("text/json;charset=utf-8");
        //塞到HttpServletResponse中返回给前台
        httpServletResponse.getWriter().write(gson.toJson(vo));
    }
}
