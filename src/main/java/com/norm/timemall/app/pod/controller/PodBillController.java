package com.norm.timemall.app.pod.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.mo.Bill;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.service.DataPolicyService;
import com.norm.timemall.app.pod.domain.dto.PodBillPageDTO;
import com.norm.timemall.app.pod.domain.ro.PodBillsRO;
import com.norm.timemall.app.pod.domain.vo.PodBillsPageVO;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.pod.service.PodBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 账单
 */
@RestController
public class PodBillController {

    @Autowired
    private FileStoreService fileStoreService;

    @Autowired
    private PodBillService podBillService;

    @Autowired
    private DataPolicyService dataPolicyService;


    /**
     * 收费凭证上传
     * @param billId
     * @param user
     * @param file
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/web_epod/bill/{bill_id}/voucher")
    public SuccessVO replaceVoucher(@PathVariable("bill_id") String billId, @AuthenticationPrincipal CustomizeUser user,
                                    @RequestParam("file") MultipartFile file){
        // 查询原uri
        Bill bill = podBillService.findBillByIdAndCustomer(billId,user.getUserId());
        if(bill == null){
            throw new ErrorCodeException(CodeEnum.BILL_NOT_EXIST);
        }
        // 存储文件
        String uri = fileStoreService.storeWithSpecifiedDir(file, FileStoreDir.VOUCHER);
        // 更新数据库 凭证uri
        podBillService.modifyBillVoucherUri(billId,uri);

        // 删除被替换的文件
        fileStoreService.deleteFile(bill.getVoucher());

        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /**
     * 标记账单状态
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/web_epod/bill/{bill_id}/mark")
    public SuccessVO markBill(@PathVariable("bill_id") String billId,@RequestParam String code)
    {
        // todo code状态校验
        // bill_Id 合法性校验
        boolean checked = dataPolicyService.billIdCheck(billId);
        if(!checked)
        {
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        podBillService.markBillByIdAndCode(billId,code);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /*
     * 分页查询用户帐单
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/web_epod/me/bill")
    public PodBillsPageVO retrieveBills(@Validated PodBillPageDTO PageDTO, @AuthenticationPrincipal CustomizeUser user)
    {
        IPage<PodBillsRO> bills = podBillService.findBills(PageDTO,user);
        PodBillsPageVO billsPageVO = new PodBillsPageVO();
        billsPageVO.setResponseCode(CodeEnum.SUCCESS);
        billsPageVO.setBills(bills);
        return billsPageVO;
    }

}
