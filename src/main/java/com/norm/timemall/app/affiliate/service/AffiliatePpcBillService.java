package com.norm.timemall.app.affiliate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.dto.PpcBillPageDTO;
import com.norm.timemall.app.affiliate.domain.ro.PpcBillPageRO;
import org.springframework.stereotype.Service;

@Service
public interface AffiliatePpcBillService {
    IPage<PpcBillPageRO> findPpcBillPage(PpcBillPageDTO dto);
}
