package com.norm.timemall.app.pod.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.pod.domain.ro.PodTransRO;
import org.springframework.stereotype.Service;

@Service
public interface PodOrderDetailService {
    IPage<PodTransRO> findTrans(PageDTO transPageDTO, CustomizeUser userDetails);
}
