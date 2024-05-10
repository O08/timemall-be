package com.norm.timemall.app.base.security;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

/**
 *在Spring Security中，SessionDestroyedEvent事件是由SessionDestroyedEventPublisher类触发的。当HttpSession过期或手动失效时，
 * 会HttpSessionEventPublisher类的sessionDestroyed方法，这个方法会发布SessionDestroyedEvent事件。
 * 为了解决HttpSession的setMaxInactiveInterval方法不会触发Spring Security 的SessionDestroyedEvent事件的问题，可以自定义一个HttpSessionListener，监听HttpSession的创建和销毁事件，并在销毁事件中手动发布SessionDestroyedEvent事件。
 */
@Component
public class CustomHttpSessionListener implements HttpSessionListener {

    @Autowired
    private ApplicationEventPublisher eventPublisher;


    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // HttpSession创建事件
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // HttpSession销毁事件
        eventPublisher.publishEvent(new HttpSessionDestroyedEvent(se.getSession()));
    }
}