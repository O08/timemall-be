package com.norm.timemall.app.open.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.config.env.EnvBean;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.mo.AffiliateOutreachChannel;
import com.norm.timemall.app.open.domain.dto.OpenFetchChoiceProductPageDTO;
import com.norm.timemall.app.open.domain.ro.OpenFetchChoiceProductRO;
import com.norm.timemall.app.open.mapper.OpenAffiliateInfluencerProductMapper;
import com.norm.timemall.app.open.mapper.OpenAffiliateOutreachChannelMapper;
import com.norm.timemall.app.open.service.OpenAffiliateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenAffiliateServiceImpl implements OpenAffiliateService {

    @Autowired
    private EnvBean envBean;
    @Autowired
    private OpenAffiliateInfluencerProductMapper openAffiliateInfluencerProductMapper;
    @Autowired
    private OpenAffiliateOutreachChannelMapper openAffiliateOutreachChannelMapper;
    @Override
    public IPage<OpenFetchChoiceProductRO> findChoiceProduct(OpenFetchChoiceProductPageDTO dto) {
        //validate chn
        LambdaQueryWrapper<AffiliateOutreachChannel> lambdaQueryWrapper= Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(AffiliateOutreachChannel::getBrandId,dto.getInfluencer())
                .eq(AffiliateOutreachChannel::getId,dto.getChn());
        boolean validated = openAffiliateOutreachChannelMapper.exists(lambdaQueryWrapper);
        if(!validated){
            throw new ErrorCodeException(CodeEnum.AFFILIATE_INFLUENCER_OR_CHANNEL_NOT_EXISTS);
        }
        IPage<OpenFetchChoiceProductRO>  page  = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        dto.setWebsite(envBean.getWebsite());
        return openAffiliateInfluencerProductMapper.selectPageByDTO(page,dto);
    }
}
