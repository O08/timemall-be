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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;


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

    @Autowired
    private AiSecurityProperties aiSecurityProperties;

    @Autowired
    private JwtToCustomizeUserConverter jwtToCustomizeUserConverter;

    @Autowired
    private CustomizeAccessDeniedHandler accessDeniedHandler;




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .addFilterBefore(new AiApiKeyFilter(aiSecurityProperties), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorizeHttpRequests -> {

                    authorizeHttpRequests.requestMatchers("/api/v1/web_mall/**",
                                    "/api/v1/app/feed_channel/*/guide",
                                    "api/v1/app/feed/list",
                                    "/api/open/**",
                                    "/api/public/**",
                                    "/api/file/{fileName:.+}/**",
                                    "/api/payment/alipay",
                                    "/api/v1/marketing/puzzle","/api/v1/marketing/puzzle/dreams",
                                    "/api/v1/data_layer/cell/indices").permitAll(); // 不需要认证和授权

                    //  AI 授权路径白名单：只有这几个地方允许 AI_BOT 或 USER 进
                    if (aiSecurityProperties.getAuthPaths() != null) {
                        for (String path : aiSecurityProperties.getAuthPaths()) {
                            authorizeHttpRequests.requestMatchers(path).hasAnyRole("AI_BOT", "USER");
                        }
                    }

                    // 除上述路径外，所有其他请求都必须拥有 ROLE_USER
                    // 只有 ROLE_AI_BOT 的 Token 在这里会被直接拦截（403）
                    authorizeHttpRequests.anyRequest().hasRole("USER");

                    })
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtToCustomizeUserConverter))
                        .accessDeniedHandler(accessDeniedHandler) // 必须在这里也加一遍
                        .authenticationEntryPoint(authenticationEntryPoint)
                )
                .logout(logout-> logout.logoutUrl("/api/v1/web_mall/logout").permitAll()
                        .logoutSuccessHandler(logoutSuccessHandler) //登出成功处理逻辑
                        .deleteCookies("JSESSIONID") )
                .exceptionHandling((exceptions) -> exceptions.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(sessionManagement ->
                         sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).maximumSessions(1)
                        .sessionRegistry(sessionRegistry()).expiredSessionStrategy(sessionInformationExpiredStrategy));

        return http.build();

    }


    @Bean
    public JwtDecoder jwtDecoder() {

        SecretKey secretKey = new SecretKeySpec(
                aiSecurityProperties.getJwtSecret().getBytes(StandardCharsets.UTF_8),
                "HmacSHA256"
        );

        // 显式指定算法，防止算法预测攻击（Security Best Practice）
        return NimbusJwtDecoder.withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
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