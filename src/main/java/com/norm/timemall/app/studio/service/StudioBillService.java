package com.norm.timemall.app.studio.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.studio.domain.dto.StudioBrandBillPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioBillRO;
import org.springframework.stereotype.Service;

@Service
public interface StudioBillService {

    IPage<StudioBillRO> findBills(String userId,StudioBrandBillPageDTO dto);

}
