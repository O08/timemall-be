package com.norm.timemall.app.studio.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.BillMarkEnum;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.service.DataPolicyService;
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
    @Autowired
    private DataPolicyService dataPolicyService;
    /**
     *
     * 商家账单
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/web_estudio/brand/bill")
    public StudioBillPageVO retrieveBills(
                                          @AuthenticationPrincipal CustomizeUser user,
                                          @Validated StudioBrandBillPageDTO dto)
    {
        IPage<StudioBillRO> bills = studioBillService.findBills(user.getBrandId(),dto);
        StudioBillPageVO vo = new StudioBillPageVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setBills(bills);
        return vo;
    }
    /**
     * 标记账单状态
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/web_estudio/bill/{bill_id}/mark")
    public SuccessVO markBillsForBrand(@PathVariable("bill_id") String billId,@RequestParam String code){
        // bill_Id 合法性校验
        boolean checked = dataPolicyService.billCanMarkAsPendingForBrand(billId);
        if(!checked)
        {
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        studioBillService.markBillForBrandByIdAndCode(billId,code);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

}
