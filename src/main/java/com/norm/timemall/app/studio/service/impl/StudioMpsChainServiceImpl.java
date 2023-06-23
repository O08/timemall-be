package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.MpsChainTagEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.MpsChain;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.studio.domain.dto.StudioMpsChainPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioNewMpsChainDTO;
import com.norm.timemall.app.studio.domain.dto.StudioPutMpsChainDTO;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchAChain;
import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsChainRO;
import com.norm.timemall.app.studio.mapper.StudioMpsChainMapper;
import com.norm.timemall.app.studio.service.StudioMpsChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StudioMpsChainServiceImpl implements StudioMpsChainService {
    @Autowired
    private StudioMpsChainMapper studioMpsChainMapper;
    @Autowired
    private AccountService accountService;

    @Override
    public IPage<StudioFetchMpsChainRO> fetchMpsChainPage(StudioMpsChainPageDTO dto) {
        String brandId = accountService.
                findBrandInfoByUserId(SecurityUserHelper.getCurrentPrincipal().getUserId())
                .getId();
        dto.setBrandId(brandId);
        IPage<StudioFetchMpsChainRO> page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        IPage<StudioFetchMpsChainRO> chain =studioMpsChainMapper.selectMpsChainPage(page,dto);
        return chain;
    }

    @Override
    public void newMpsChain(StudioNewMpsChainDTO dto) {

        String brandId = accountService.
                findBrandInfoByUserId(SecurityUserHelper.getCurrentPrincipal().getUserId())
                .getId();

        MpsChain chain = new MpsChain();
        chain.setId(IdUtil.simpleUUID())
                .setBrandId(brandId)
                .setTitle(dto.getTitle())
                .setProcessedCnt(0)
                .setProcessingCnt(0)
                .setTag(MpsChainTagEnum.PUBLISH.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        studioMpsChainMapper.insert(chain);

    }

    @Override
    public void modifyMpsChain(StudioPutMpsChainDTO dto) {

        studioMpsChainMapper.updateMpsChainTitleAndTagById(dto);
    }

    @Override
    public StudioFetchAChain findChainInfo(String chainId) {
        MpsChain chain = studioMpsChainMapper.selectById(chainId);
        StudioFetchAChain bo = new StudioFetchAChain();
        bo.setTitle(chain.getTitle());
        bo.setTag(chain.getTag());
        return bo;
    }
}
