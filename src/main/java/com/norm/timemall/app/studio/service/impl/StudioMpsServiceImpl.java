package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.MpsTagEnum;
import com.norm.timemall.app.base.enums.MpsTypeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Mps;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.studio.domain.dto.StudioFetchMpsListPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioNewMpsDTO;
import com.norm.timemall.app.studio.domain.dto.StudioTaggingMpsDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsListRO;
import com.norm.timemall.app.studio.mapper.StudioMpsMapper;
import com.norm.timemall.app.studio.service.StudioMpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StudioMpsServiceImpl implements StudioMpsService {
    @Autowired
    private StudioMpsMapper studioMpsMapper;
    @Autowired
    private AccountService accountService;
    @Override
    public IPage<StudioFetchMpsListRO> fetchMpsList(StudioFetchMpsListPageDTO dto) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        dto.setBrandId(brandId);

        IPage<StudioFetchMpsListRO> page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        IPage<StudioFetchMpsListRO> mps=studioMpsMapper.selectMpsListPage(page,dto);
        return mps;
    }

    @Override
    public Mps  newMps(StudioNewMpsDTO dto) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        Mps mps = new Mps();
        mps.setId(IdUtil.simpleUUID())
                .setMpsType(MpsTypeEnum.FROM_PLAN.getMark())
                .setBrandId(brandId)
                .setTag(MpsTagEnum.CREATED.getMark())
                .setChainId(dto.getChainId())
                .setTitle(dto.getTitle())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        studioMpsMapper.insert(mps);
        return mps;
    }

    @Override
    public void taggingMps(StudioTaggingMpsDTO dto) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        studioMpsMapper.updateTagById(dto,brandId);
    }

    @Override
    public Mps findMps(String mpsId) {

        return  studioMpsMapper.selectById(mpsId);
    }
}
