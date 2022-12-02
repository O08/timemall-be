package com.norm.timemall.app.studio.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.studio.domain.dto.StudioBrandBillPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioBillRO;
import com.norm.timemall.app.studio.domain.vo.StudioBillPageVO;
import com.norm.timemall.app.studio.service.StudioBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudioBillController {
    @Autowired
    private StudioBillService studioBillService;
    /**
     *
     * 商家账单
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/web_estudio/brand/bill")
    public StudioBillPageVO retrieveBills(
                                          @AuthenticationPrincipal CustomizeUser user,
                                          @Validated @RequestBody StudioBrandBillPageDTO dto)
    {
        IPage<StudioBillRO> bills = studioBillService.findBills(user.getUserId(),dto);
        StudioBillPageVO vo = new StudioBillPageVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setBills(bills);
        return vo;
    }
}
