package com.norm.timemall.app.open.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.open.domain.dto.OpenFetchChoiceProductPageDTO;
import com.norm.timemall.app.open.domain.ro.OpenFetchChoiceProductRO;
import org.springframework.stereotype.Service;

@Service
public interface OpenAffiliateService {
    IPage<OpenFetchChoiceProductRO> findChoiceProduct(OpenFetchChoiceProductPageDTO dto);
}
