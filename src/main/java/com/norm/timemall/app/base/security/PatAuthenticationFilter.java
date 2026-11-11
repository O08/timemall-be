package com.norm.timemall.app.base.security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class PatAuthenticationFilter extends OncePerRequestFilter {

    private final PatAuthenticationProvider patAuthenticationProvider;

    // 构造函数：直接传入核心的认证 Provider 即可
    public PatAuthenticationFilter(PatAuthenticationProvider patAuthenticationProvider) {
        this.patAuthenticationProvider = patAuthenticationProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        // 如果没有 Token 或者不是 PAT Token 格式，直接放行给后面的过滤器（如 JWT 过滤器）处理
        if (header == null || !header.startsWith("Bearer BV_PAT_")) {
            filterChain.doFilter(request, response);
            return;
        }

        String rawToken = header.substring(7); // 截取 "BV_PAT_..."

        try {
            // 直接调用 Provider 内部的核对逻辑（算哈希、查 Redis/DB、验过期）
            PatUserDetails userDetails = patAuthenticationProvider.validateAndFetchDetails(rawToken);

            // 验证通过！直接赋予 ROLE_AI_BOT 权限 并 构建 Spring Security 标准的已认证凭证，并塞入上下文（注入 User 完成）
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response); // 继续向后

        } catch (Exception failed) {
            // 5. 失败则清理上下文，并直接拦截返回 401 错误
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\": 403, \"msg\": \"Access Deny\"}");
            return;
        }

    }
}
