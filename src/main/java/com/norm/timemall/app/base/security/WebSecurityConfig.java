package com.norm.timemall.app.base.security;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


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
    @Resource
    private PasswordEncoder passwordEncoder;




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeHttpRequests -> {
                    authorizeHttpRequests.requestMatchers("/api/v1/web_mall/**",
                                    "/api/v1/app/feed_channel/*/guide",
                                    "api/v1/app/feed/list",
                                    "/api/open/**",
                                    "/api/public/**",
                                    "/api/file/{fileName:.+}/**",
                                    "/api/payment/alipay",
                                    "/api/v1/marketing/puzzle","/api/v1/marketing/puzzle/dreams",
                                    "/api/v1/data_layer/cell/indices").permitAll() // 不需要认证和授权
                            .anyRequest().authenticated(); // 其他请求都需要授权后才能使用

                    })
                .logout(logout-> logout.logoutUrl("/api/v1/web_mall/logout").permitAll()
                        .logoutSuccessHandler(logoutSuccessHandler) //登出成功处理逻辑
                        .deleteCookies("JSESSIONID") )
                .exceptionHandling((exceptions) -> exceptions.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(sessionManagement -> sessionManagement.maximumSessions(1).sessionRegistry(sessionRegistry()).expiredSessionStrategy(sessionInformationExpiredStrategy));

        return http.build();

    }



    @Bean
    public WechatQrAuthenticationProvider wechatQrAuthenticationProvider() {
        return new WechatQrAuthenticationProvider();
    }

    @Bean
    public PhoneOrEmailAuthenticationProvider phoneOrEmailAuthenticationProvider() {
        return new PhoneOrEmailAuthenticationProvider();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        return daoAuthenticationProvider;
    }



    /**
     * 加载微信验证码登录
     */
@Bean
    WechatQrCodeLoginFilter wechatQrCodeLoginFilter(HttpSecurity http) throws Exception {
        WechatQrCodeLoginFilter wechatQrCodeLoginFilter = new WechatQrCodeLoginFilter();
        //自定义登录url
        wechatQrCodeLoginFilter.setFilterProcessesUrl("/api/v1/web_mall/do_wechat_qrCode_sign_in");
        wechatQrCodeLoginFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        wechatQrCodeLoginFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        wechatQrCodeLoginFilter.setAuthenticationManager(authenticationManager(http));
        wechatQrCodeLoginFilter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());
        wechatQrCodeLoginFilter.setSecurityContextHolderStrategyWechat(new HttpSessionSecurityContextRepository());
        wechatQrCodeLoginFilter.setSessionRegistry(sessionRegistry());
        return wechatQrCodeLoginFilter;
    }

    /**
     * 加载手机、邮箱用户登录
     */
    @Bean
    PhoneOrEmailLoginFilter phoneOrEmailLoginFilter(HttpSecurity http) throws Exception {

        PhoneOrEmailLoginFilter phoneOrEmailLoginFilter = new PhoneOrEmailLoginFilter();
        //自定义登录url
        phoneOrEmailLoginFilter.setFilterProcessesUrl("/api/v1/web_mall/email_or_phone_sign_in");
        phoneOrEmailLoginFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        phoneOrEmailLoginFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        phoneOrEmailLoginFilter.setAuthenticationManager(authenticationManager(http));
        phoneOrEmailLoginFilter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());
        phoneOrEmailLoginFilter.setSecurityContextHolderStrategyWechat(new HttpSessionSecurityContextRepository());
        phoneOrEmailLoginFilter.setSessionRegistry(sessionRegistry());
        return phoneOrEmailLoginFilter;

    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{

        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());//原来默认的
        authenticationManagerBuilder.authenticationProvider(wechatQrAuthenticationProvider());//自定义的
        authenticationManagerBuilder.authenticationProvider(phoneOrEmailAuthenticationProvider());

        return authenticationManagerBuilder.build();
    }
    @Bean
    public SessionRegistry sessionRegistry() {
      return new SessionRegistryImpl();
    }


    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}