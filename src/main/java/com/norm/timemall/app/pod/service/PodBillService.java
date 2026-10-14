package com.norm.timemall.app.pod.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.Bill;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.pod.domain.dto.PodBillPageDTO;
import com.norm.timemall.app.pod.domain.ro.FetchBillDetailRO;
import com.norm.timemall.app.pod.domain.ro.PodBillsRO;
import org.springframework.stereotype.Service;

@Service
public interface PodBillService {
    Bill findBillByIdAndCustomer(String billId,String customerId);
    IPage<PodBillsRO> findBills(PodBillPageDTO pageDTO, CustomizeUser user);

    void pay(String billId);

    FetchBillDetailRO findbillDetail(String id);
}
