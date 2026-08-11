package com.norm.timemall.app.team.helper;

import cn.hutool.core.util.IdUtil;

import com.norm.timemall.app.base.mo.FinDistribute;

import com.norm.timemall.app.team.mapper.TeamFinDistributeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TeamCommissionHelper {


    @Autowired
    private TeamFinDistributeMapper teamFinDistributeMapper;

    public void createFinDistributeIfNotExists(String oasisId, String brandId){
        FinDistribute finDistribute = teamFinDistributeMapper.selectDistributeByBrandIdAndOasisIdForUpdate(brandId, oasisId);
        if(finDistribute == null){
            finDistribute = new FinDistribute();
            finDistribute.setId(IdUtil.simpleUUID())
                    .setOasisId(oasisId)
                    .setBrandId(brandId)
                    .setAmount(BigDecimal.ZERO);
            teamFinDistributeMapper.insert(finDistribute);
        }

    }
}
