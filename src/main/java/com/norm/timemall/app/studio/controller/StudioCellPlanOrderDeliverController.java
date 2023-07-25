package com.norm.timemall.app.studio.controller;

import cn.hutool.core.util.StrUtil;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CellPlanOrderTagEnum;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.pojo.FetchCellPlanOrderDeliver;
import com.norm.timemall.app.base.pojo.vo.CellPlanOrderDeliverVO;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.studio.service.StudioApiAccessControlService;
import com.norm.timemall.app.studio.service.StudioCellPlanOrderDeliverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class StudioCellPlanOrderDeliverController {
    @Autowired
    private StudioCellPlanOrderDeliverService studioCellPlanOrderDeliverService;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private StudioApiAccessControlService studioApiAccessControlService;

    @PostMapping("/api/v1/web_estudio/cell/plan_order/new_deliver")
    public SuccessVO addMpsPaperDeliver(@RequestParam("preview") MultipartFile preview,
                                        @RequestParam("deliver") MultipartFile deliver,
                                        @RequestParam("orderId") String orderId
    ){
        // validate
        if(preview.isEmpty()|| deliver.isEmpty()|| StrUtil.isBlank(orderId)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        boolean checked = studioApiAccessControlService.isCellPlanOrderSupplierAndCheckOrderTag(orderId, CellPlanOrderTagEnum.DELIVERING.ordinal()+"");
        if(!checked){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // store file in classified
        String deliverUri = fileStoreService.storeWithLimitedAccess(deliver, FileStoreDir.CELL_PLAN_DELIVER);
        String previewUri = fileStoreService.storeWithLimitedAccess(preview, FileStoreDir.CELL_PLAN_PREVIEW);
        String previewName=preview.getOriginalFilename();
        String deliverName=deliver.getOriginalFilename();
        studioCellPlanOrderDeliverService.newDeliver(orderId,previewUri,deliverUri,previewName,deliverName);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @GetMapping("/api/v1/web_estudio/brand/cell/plan_order/{orderId}/deliver")
    public CellPlanOrderDeliverVO fetchBrandMpsPaperDeliver(@PathVariable("orderId") String orderId){

        FetchCellPlanOrderDeliver deliver = studioCellPlanOrderDeliverService.findBrandCellPlanOrderDeliver(orderId);
        CellPlanOrderDeliverVO vo = new CellPlanOrderDeliverVO();
        vo.setDeliver(deliver);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }

}
