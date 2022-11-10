package com.norm.timemall.app.base.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    //匿名用户访问无权限资源时的异常
    @Autowired
    CustomizeAuthenticationEntryPoint authenticationEntryPoint;

    //登录成功处理逻辑
    @Autowired
    CustomizeAuthenticationSuccessHandler authenticationSuccessHandler;

    //登录失败处理逻辑
    @Autowired
    CustomizeAuthenticationFailureHandler authenticationFailureHandler;

    //登出成功处理逻辑
    @Autowired
    CustomizeLogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    CustomizeSessionInformationExpiredStrategy sessionInformationExpiredStrategy;





    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService);
        authenticationManager = authenticationManagerBuilder.build();

        http.csrf().disable().cors().disable().authorizeHttpRequests()
//                .anyRequest().permitAll();
//
                .antMatchers("/api/v1/web_mall/email_join", "/api/v1/account/auth").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginProcessingUrl("/api/v1/web_mall/email_sign_in")
                    .permitAll().//允许所有用户
                    successHandler(authenticationSuccessHandler).
                    failureHandler(authenticationFailureHandler)
                .and().logout()
                    .permitAll()
                    .logoutSuccessHandler(logoutSuccessHandler) //登出成功处理逻辑
                    .deleteCookies("JSESSIONID") //登出之后删除cookie
                .and().exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint)
                .and().authenticationManager(authenticationManager)
                    .sessionManagement().maximumSessions(1)
                    .expiredSessionStrategy(sessionInformationExpiredStrategy);

        return http.build();

    }

}