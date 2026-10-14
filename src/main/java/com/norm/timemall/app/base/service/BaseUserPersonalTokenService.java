package com.norm.timemall.app.base.service;

import com.norm.timemall.app.base.pojo.BaseCreatePatBO;
import com.norm.timemall.app.base.pojo.dto.BaseCreatePatDTO;
import com.norm.timemall.app.base.pojo.ro.PatListRO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BaseUserPersonalTokenService {
    List<PatListRO> listUserTokens();

    BaseCreatePatBO createToken(BaseCreatePatDTO dto);

    void revokeToken(String tokenId);
}
