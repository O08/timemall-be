package com.norm.timemall.app.affiliate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.dto.PpcBillPageDTO;
import com.norm.timemall.app.affiliate.domain.ro.PpcBillPageRO;
import com.norm.timemall.app.affiliate.domain.vo.PpcBillPageVO;
import com.norm.timemall.app.affiliate.service.AffiliatePpcBillService;
import com.norm.timemall.app.base.enums.CodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PpcBillController {
    @Autowired
    private AffiliatePpcBillService affiliatePpcBillService;
@GetMapping("/api/v1/web/affiliate/ppc/bill")
    public PpcBillPageVO fetchPpcBillPage(@Validated PpcBillPageDTO dto){
        IPage<PpcBillPageRO> bill=affiliatePpcBillService.findPpcBillPage(dto);
        PpcBillPageVO vo = new PpcBillPageVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setBill(bill);
        return vo;

    }
}
