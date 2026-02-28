package com.norm.timemall.app.base.security;

import com.google.gson.Gson;
import com.norm.timemall.app.base.entity.ErrorVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LogAndSuppressRequestRejectedExceptionFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(req, res);
        } catch (RequestRejectedException e) {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;

            log
                    .warn(
                            "request_rejected: remote={}, user_agent={}, request_url={}",
                            request.getRemoteHost(),
                            request.getHeader(HttpHeaders.USER_AGENT),
                            request.getRequestURL(),
                            e
                    );

            ErrorVO vo = new ErrorVO(CodeEnum.REQUEST_REJECTED);
            Gson gson = new Gson();
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write(gson.toJson(vo));

        }
    }
}
