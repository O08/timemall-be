package com.norm.timemall.app.indicator.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.norm.timemall.app.base.enums.IndDataLayerCellIndicesEventEnum;
import com.norm.timemall.app.base.mo.AffiliateAccess;
import com.norm.timemall.app.base.util.IpLocationUtil;
import com.norm.timemall.app.indicator.domain.dto.IndDataLayerCellIndicesDTO;
import com.norm.timemall.app.indicator.mapper.IndAffiliateAccessMapper;
import com.norm.timemall.app.indicator.service.IndAffiliateAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class IndAffiliateAccessServiceImpl implements IndAffiliateAccessService {
    @Autowired
    private IndAffiliateAccessMapper affiliateAccessMapper;
    @Override
    public void newAccess(IndDataLayerCellIndicesDTO dto, HttpServletRequest request) {

        // is valid param
        if(    (!IndDataLayerCellIndicesEventEnum.CLICKS.getMark().equals(dto.getEvent()))
                || CollUtil.isEmpty(dto.getCell().getClicks())
                || ObjectUtil.isNull(dto.getAffiliate())
                ||  CharSequenceUtil.isBlank(dto.getAffiliate().getInfluencer())
                || CharSequenceUtil.isBlank(dto.getAffiliate().getChn())
                || CharSequenceUtil.isBlank(dto.getAffiliate().getMarket())
        ){
            return;
        }
        // get ip location
        String ip= IpLocationUtil.getIpAddress(request);
        String ipLocation = IpLocationUtil.getCityInfo(ip);
        String cellId=dto.getCell().getClicks().get(0).getCellId();
        // new access log
        AffiliateAccess affiliateAccess=new AffiliateAccess();
        affiliateAccess.setId(IdUtil.simpleUUID())
                .setIp(ip)
                .setIpLocation(ipLocation)
                .setInfluencer(dto.getAffiliate().getInfluencer())
                .setOutreachChannelId(dto.getAffiliate().getChn())
                .setCellId(cellId)
                .setMarket(dto.getAffiliate().getMarket())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        affiliateAccessMapper.insert(affiliateAccess);

    }
}
