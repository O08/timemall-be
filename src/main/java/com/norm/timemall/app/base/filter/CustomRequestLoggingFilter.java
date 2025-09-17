package com.norm.timemall.app.base.filter;

import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.security.CustomizeUser;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import jakarta.servlet.http.HttpServletRequest;

public class CustomRequestLoggingFilter extends CommonsRequestLoggingFilter {
    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        Authentication authentication = SecurityUserHelper.getCurrentUserAuthentication();
        String userId= authentication == null || !(authentication.getPrincipal() instanceof  CustomizeUser) ? "" : ((CustomizeUser)authentication.getPrincipal()).getUserId();
        logger.debug("Current Request User is "+ userId);
        logger.debug(message);
    }
}
