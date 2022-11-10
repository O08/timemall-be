package com.norm.timemall.app.studio.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioTransRO;
import org.springframework.stereotype.Service;

@Service
public interface StudioOrderDetailsService {
    IPage<StudioTransRO> findTrans(String brandId, String userId,PageDTO dto);
}
