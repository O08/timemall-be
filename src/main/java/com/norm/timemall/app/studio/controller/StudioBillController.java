package com.norm.timemall.app.studio.controller;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.BillCategoiesEnum;
import com.norm.timemall.app.base.enums.BillMarkEnum;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.mo.Bill;
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

        if(CharSequenceUtil.isBlank(code)){
            throw new QuickMessageException("code required");
        }
        // query bill
        Bill bill =studioBillService.findOneBill(billId);
        if(bill==null){
            throw new QuickMessageException("未找到相关账单");
        }

        boolean checked = false;
        if(BillCategoiesEnum.CELL.getMark().equals(bill.getCategories())){
             checked = dataPolicyService.billCanMarkAsPendingForBrand(billId);
        }
        if(BillCategoiesEnum.PROPOSAL.getMark().equals(bill.getCategories())){
            checked = dataPolicyService.proposalBillCanMarkAsPendingForBrand(billId);
        }

        if(!checked)
        {
            throw new QuickMessageException("权限校验不通过，操作失败");
        }
        studioBillService.markBillForBrandByIdAndCode(billId,code);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

}
