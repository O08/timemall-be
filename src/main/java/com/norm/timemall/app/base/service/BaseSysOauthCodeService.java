package com.norm.timemall.app.base.service;

import org.springframework.stereotype.Service;

@Service
public interface BaseSysOauthCodeService {
    String findUserIdUsingCode(String code);

    void removeOneJwtCode(String code);

    void createOneJwtCode(String code, String userId);
}
