package com.norm.timemall.app.base.security;

import cn.hutool.core.util.IdUtil;
import com.google.gson.Gson;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.BrandMarkEnum;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.base.service.BaseSysOauthCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @Author: Hutengfei
 * @Description: 登录成功处理逻辑
 * @Date Create in 2019/9/3 15:52
 */
@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Autowired
    private AccountService accountService;
    @Autowired
    private BaseSysOauthCodeService baseSysOauthCodeService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String redirectUri = httpServletRequest.getParameter("redirect_uri");
        String state = httpServletRequest.getParameter("state"); // Extract state from platform


        CustomizeUser userDetail =(CustomizeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        accountService.modifyAccountMark(BrandMarkEnum.ONLINE.getMark(),userDetail.getBrandId());


        //返回json数据
        Gson gson = new Gson();
        SuccessVO vo = new SuccessVO(CodeEnum.SUCCESS);
        if (StringUtils.hasText(redirectUri)) {
            // Generate a safe temporary code
            String code = IdUtil.simpleUUID();

            //  Burn-after-reading: Save to MySQL (expires in 5 minutes)
            baseSysOauthCodeService.createOneJwtCode(code,userDetail.getUserId());

            // Construct Redirect URL (Using the real state)
            String finalRedirect = redirectUri + (redirectUri.contains("?") ? "&" : "?")
                    + "code=" + code
                    + "&state=" + (StringUtils.hasText(state) ? state : "");

            // Set Data for Vue
            vo.setData(Map.of("oauthRedirect", finalRedirect));
        }
        //处理编码方式，防止中文乱码的情况
        httpServletResponse.setContentType("application/json;charset=utf-8");        //塞到HttpServletResponse中返回给前台
        httpServletResponse.getWriter().write(gson.toJson(vo));
    }
}
