package com.norm.timemall.app.base.security;

import com.norm.timemall.app.base.enums.BrandMarkEnum;
import com.norm.timemall.app.base.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LogoutListener implements ApplicationListener<SessionDestroyedEvent> {
    @Autowired
    private AccountService accountService;
    @Override
    public void onApplicationEvent(SessionDestroyedEvent event)
    {
        List<SecurityContext> lstSecurityContext = event.getSecurityContexts();
        CustomizeUser userDetail;
        for (SecurityContext securityContext : lstSecurityContext)
        {
            userDetail = (CustomizeUser) securityContext.getAuthentication().getPrincipal();
            // update user as offline
            accountService.modifyAccountMark(BrandMarkEnum.OFFLINE.getMark(), userDetail.getBrandId());
        }
    }

}
