package com.norm.timemall.app.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.mall.domain.dto.BrandGuideDTO;
import com.norm.timemall.app.mall.domain.dto.MallRetrieveBrandProductListPageDTO;
import com.norm.timemall.app.mall.domain.dto.MallRetrieveProductListPageDTO;
import com.norm.timemall.app.mall.domain.pojo.MallHomeInfo;
import com.norm.timemall.app.mall.domain.ro.MallRetrieveBrandProductListPageRO;
import com.norm.timemall.app.mall.domain.ro.MallRetrieveProductListPageRO;
import com.norm.timemall.app.mall.domain.vo.MallFetchVirtualProductProfileVO;
import org.springframework.stereotype.Service;

@Service
public interface MallVirtualProductService {
    IPage<MallRetrieveProductListPageRO> findProducts(MallRetrieveProductListPageDTO dto);

    IPage<MallRetrieveBrandProductListPageRO> findBrandProducts(MallRetrieveBrandProductListPageDTO dto);

    MallFetchVirtualProductProfileVO findProductProfile(String id);

    MallHomeInfo findHomeInfo(BrandGuideDTO dto);

}
