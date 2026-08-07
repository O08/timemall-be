package com.norm.timemall.app.mall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.mall.domain.dto.BrandGuideDTO;
import com.norm.timemall.app.mall.domain.dto.MallRetrieveBrandProductListPageDTO;
import com.norm.timemall.app.mall.domain.dto.MallRetrieveProductListPageDTO;
import com.norm.timemall.app.mall.domain.pojo.MallHomeInfo;
import com.norm.timemall.app.mall.domain.ro.MallFetchVirtualProductProfileRO;
import com.norm.timemall.app.mall.domain.ro.MallRetrieveBrandProductListPageRO;
import com.norm.timemall.app.mall.domain.ro.MallRetrieveProductListPageRO;
import com.norm.timemall.app.mall.domain.vo.MallFetchVirtualProductProfileVO;
import com.norm.timemall.app.mall.mapper.MallVirtualProductMapper;
import com.norm.timemall.app.mall.service.MallVirtualProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MallVirtualProductServiceImpl implements MallVirtualProductService {

    @Autowired
    private MallVirtualProductMapper mallVirtualProductMapper;

    @Override
    public IPage<MallRetrieveProductListPageRO> findProducts(MallRetrieveProductListPageDTO dto) {

        Page<MallRetrieveProductListPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return mallVirtualProductMapper.selectPageByQ(page,dto);

    }

    @Override
    public IPage<MallRetrieveBrandProductListPageRO> findBrandProducts(MallRetrieveBrandProductListPageDTO dto) {

        Page<MallRetrieveBrandProductListPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return mallVirtualProductMapper.selectPageByBrandAndQ(page,dto);

    }

    @Override
    public MallFetchVirtualProductProfileVO findProductProfile(String id) {

        MallFetchVirtualProductProfileRO profile = mallVirtualProductMapper.selectProfileInfo(id);
        MallFetchVirtualProductProfileVO vo = new MallFetchVirtualProductProfileVO();
        vo.setProfile(profile==null? new MallFetchVirtualProductProfileRO() : profile);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }

    @Override
    public MallHomeInfo findHomeInfo(BrandGuideDTO dto) {
        return mallVirtualProductMapper.selectHomeInfoByBrandIdOrHandle(dto);
    }
}
