package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsFund;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface StudioMpsFundService {
    StudioFetchMpsFund getMpsFundForBrand();


    void topUpToMpsFund(BigDecimal amount,String mpsFundId);

    void applyMpsFundAccount();

}
