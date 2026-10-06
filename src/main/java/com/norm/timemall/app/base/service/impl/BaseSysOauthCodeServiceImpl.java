package com.norm.timemall.app.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.mapper.BaseSysOauthCodeMapper;
import com.norm.timemall.app.base.mo.SysOauthCode;
import com.norm.timemall.app.base.service.BaseSysOauthCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class BaseSysOauthCodeServiceImpl implements BaseSysOauthCodeService {

    @Autowired
    private BaseSysOauthCodeMapper baseSysOauthCodeMapper;
    @Override
    public String findUserIdUsingCode(String code) {
        return baseSysOauthCodeMapper.selectUserIdByCode(code);
    }

    @Override
    public void removeOneJwtCode(String code) {
        LambdaQueryWrapper<SysOauthCode> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(SysOauthCode::getCode,code);
        baseSysOauthCodeMapper.delete(wrapper);
    }

    @Override
    public void createOneJwtCode(String code, String userId) {
        SysOauthCode oauthCode = new SysOauthCode();
        oauthCode.setCode(code);
        oauthCode.setUserId(userId);
        // Set expiration to 5 minutes from now
        oauthCode.setExpireTime(LocalDateTime.now().plusMinutes(5));
        baseSysOauthCodeMapper.insert(oauthCode);

    }
}
