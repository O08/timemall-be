package com.norm.timemall.app.studio.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.Bill;
import com.norm.timemall.app.studio.domain.dto.StudioBrandBillPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioBillRO;
import org.springframework.stereotype.Service;

@Service
public interface StudioBillService {

    IPage<StudioBillRO> findBills(String brandId,StudioBrandBillPageDTO dto);

    void markBillForBrandByIdAndCode(String billId, String code);

    Bill findOneBill(String billId);
}
