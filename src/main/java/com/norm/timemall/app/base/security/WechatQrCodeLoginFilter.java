package com.norm.timemall.app.base.security;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

public class WechatQrCodeLoginFilter extends AbstractAuthenticationProcessingFilter {

    private  ConcurrentSessionControlAuthenticationStrategy securityContextHolderStrategy ;

    private  SecurityContextRepository securityContextRepository ;


    private SessionRegistry sessionRegistry;
    public WechatQrCodeLoginFilter() {
        super(new AntPathRequestMatcher("/api/v1/web_mall/do_wechat_qrCode_sign_in","GET"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        // 需要是 GET 请求
        if (!request.getMethod().equals("GET")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String code = request.getParameter("code");
        String state = request.getParameter("state");

        if(StrUtil.isBlank(code) || StrUtil.isBlank(state)){
            throw new AuthenticationServiceException("INVALID PARAMETERS");
        }

        WechatQrCodeAuthenticationToken token = new WechatQrCodeAuthenticationToken(code,state);
        setDetails(request, token);
        Authentication authenticate = this.getAuthenticationManager().authenticate(token);
        return authenticate;
    }

    public void setDetails(HttpServletRequest request , WechatQrCodeAuthenticationToken token){
        token.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }
    public void setSecurityContextHolderStrategyWechat(SecurityContextRepository securityContextRepository){

        this.securityContextRepository=securityContextRepository;

    }
    public void setSessionRegistry(SessionRegistry sessionRegistry){
        this.sessionRegistry=sessionRegistry;
        this.securityContextHolderStrategy =  new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry);
        this.securityContextHolderStrategy.setMaximumSessions(1);
        this.setSessionAuthenticationStrategy(this.securityContextHolderStrategy);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        CustomizeUser securityUser = (CustomizeUser) authResult.getPrincipal();
        // 这里需要在登录用户实体类中加入一个构造方法
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(securityUser,securityUser.getUserId(),securityUser.getAuthorities()));
        sessionRegistry.registerNewSession(request.getSession().getId(),securityUser);

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authResult);
        this.securityContextRepository.saveContext(context, request, response);
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(LogMessage.format("Set SecurityContextHolder to %s", authResult));
        }

        if (this.eventPublisher != null) {
            this.eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
        }

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);

    }
}