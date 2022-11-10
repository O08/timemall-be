package com.norm.timemall.app.pod.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.mo.Bill;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.pod.domain.ro.PodBillsRO;
import org.springframework.stereotype.Service;

@Service
public interface PodBillService {
    Bill findBillByIdAndCustomer(String billId,String customerId);
    void modifyBillVoucherUri(String billId, String uri);

    void markBillByIdAndCode(String billId, String code);

    IPage<PodBillsRO> findBills(PageDTO pageDTO, CustomizeUser user);

}
