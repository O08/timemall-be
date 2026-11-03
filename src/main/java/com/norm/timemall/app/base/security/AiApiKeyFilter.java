package com.norm.timemall.app.base.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AiApiKeyFilter extends OncePerRequestFilter {
    private final AiSecurityProperties props;
    private final AntPathMatcher matcher = new AntPathMatcher();

    public AiApiKeyFilter(AiSecurityProperties props) {
        this.props = props;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String key = request.getHeader("X-Blv-AI-Key");
        String uri = request.getRequestURI();

        // 只有提供 API Key 时才进行校验
        if (key != null) {
            boolean isKeyRight = props.getApiKey().equals(key);
            boolean isPathAllowed = props.getAllowPaths().stream().anyMatch(p -> matcher.match(p, uri));

            if (isKeyRight && isPathAllowed) {
                // 情况 1：Key 对，路径也对 -> 授权并放行
                var auth = new UsernamePasswordAuthenticationToken("AI_BOT", null,
                        AuthorityUtils.createAuthorityList("ROLE_AI_BOT"));
                SecurityContextHolder.getContext().setAuthentication(auth);
                chain.doFilter(request, response); // 继续向后
            } else {
                // 情况 2：Key 错了，或者 AI 越权了 -> 直接拦截，不调用 chain.doFilter！
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\": 403, \"msg\": \"Access Deny\"}");
                // 注意：这里没有 chain.doFilter，请求到此为止
            }
            return;
        }
        chain.doFilter(request, response);
    }
}

