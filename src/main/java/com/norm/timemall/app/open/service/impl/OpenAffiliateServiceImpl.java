package com.norm.timemall.app.open.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.config.env.EnvBean;
import com.norm.timemall.app.open.domain.dto.OpenFetchChoiceProductPageDTO;
import com.norm.timemall.app.open.domain.ro.OpenFetchChoiceProductRO;
import com.norm.timemall.app.open.mapper.OpenAffiliateInfluencerProductMapper;
import com.norm.timemall.app.open.service.OpenAffiliateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenAffiliateServiceImpl implements OpenAffiliateService {

    @Autowired
    private EnvBean envBean;
    @Autowired
    private OpenAffiliateInfluencerProductMapper openAffiliateInfluencerProductMapper;
    @Override
    public IPage<OpenFetchChoiceProductRO> findChoiceProduct(OpenFetchChoiceProductPageDTO dto) {
        IPage<OpenFetchChoiceProductRO>  page  = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        dto.setWebsite(envBean.getWebsite());
        return openAffiliateInfluencerProductMapper.selectPageByDTO(page,dto);
    }
}
