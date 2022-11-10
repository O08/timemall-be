package com.norm.timemall.app.pod.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.pod.domain.ro.PodTransRO;
import com.norm.timemall.app.pod.mapper.PodOrderDetailsMapper;
import com.norm.timemall.app.pod.service.PodOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PodOrderDetailServiceImpl implements PodOrderDetailService {
    @Autowired
    private PodOrderDetailsMapper podOrderDetailsMapper;
    @Override
    public IPage<PodTransRO> findTrans(PageDTO transPageDTO, CustomizeUser user) {
        IPage<PodTransRO> page = new Page<>();
        page.setCurrent(transPageDTO.getCurrent());
        page.setSize(transPageDTO.getSize());
        IPage<PodTransRO> trans = podOrderDetailsMapper.selectTransPageByUserId(page, user.getUserId());
        return trans;
    }
}
