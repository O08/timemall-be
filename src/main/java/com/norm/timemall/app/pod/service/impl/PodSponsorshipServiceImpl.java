package com.norm.timemall.app.pod.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.pod.domain.dto.PodFetchSponsorshipPageDTO;
import com.norm.timemall.app.pod.domain.ro.PodFetchSponsorshipPageRO;
import com.norm.timemall.app.pod.mapper.PodUserSponsorshipRecordMapper;
import com.norm.timemall.app.pod.service.PodSponsorshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PodSponsorshipServiceImpl implements PodSponsorshipService {

    @Autowired
    private PodUserSponsorshipRecordMapper podSponsorshipMapper;

    @Override
    public IPage<PodFetchSponsorshipPageRO> fetchSponsorshipPage(PodFetchSponsorshipPageDTO dto) {
        Page<PodFetchSponsorshipPageRO> page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        // 当前品牌是受赞助方
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        return podSponsorshipMapper.selectSponsorshipPage(page, dto, currentBrandId);
    }
}
