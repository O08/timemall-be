package com.norm.timemall.app.studio.controller;

import cn.hutool.core.util.StrUtil;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.CommercialPaperDeliverTagEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.studio.domain.dto.StudioMpsPaperDeliverLeaveMsgDTO;
import com.norm.timemall.app.studio.domain.dto.StudioPutMpsPaperDeliverTagDTO;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsPaperDeliver;
import com.norm.timemall.app.studio.domain.vo.StudioFetchMpsPaperDeliverVO;
import com.norm.timemall.app.studio.service.StudioApiAccessControlService;
import com.norm.timemall.app.studio.service.StudioCommercialPaperDeliverService;
import com.norm.timemall.app.studio.service.StudioMpsFundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class StudioMpsPaperDeliverController {
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private StudioCommercialPaperDeliverService studioCommercialPaperDeliverService;
    @Autowired
    private StudioApiAccessControlService studioApiAccessControlService;
    @Autowired
    private StudioMpsFundService studioMpsFundService;
    @Autowired
    private OrderFlowService orderFlowService;


    @PostMapping("/api/v1/web_estudio/brand/mps/new_deliver")
    public SuccessVO addMpsPaperDeliver( @RequestParam("preview") MultipartFile preview,
                                         @RequestParam("deliver") MultipartFile deliver,
                                         @RequestParam("paperId") String paperId
                                         ){
        // validate
        if(preview.isEmpty()|| deliver.isEmpty()|| StrUtil.isBlank(paperId)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // store file in classified
        String deliverUri = fileStoreService.storeWithLimitedAccess(deliver, FileStoreDir.MPS_DELIVER);
        String previewUri = fileStoreService.storeWithLimitedAccess(preview, FileStoreDir.MPS_PREVIEW);
        String previewName=preview.getOriginalFilename();
        String deliverName=deliver.getOriginalFilename();
        studioCommercialPaperDeliverService.newDeliver(paperId,previewUri,deliverUri,previewName,deliverName);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @GetMapping("/api/v1/web_estudio/mps_paper/{paper_id}/deliver")
    public StudioFetchMpsPaperDeliverVO fetchMpsPaperDeliver(@PathVariable("paper_id") String paperId){
        StudioFetchMpsPaperDeliver deliver = studioCommercialPaperDeliverService.findMpsPaperDeliver(paperId);
        StudioFetchMpsPaperDeliverVO vo = new StudioFetchMpsPaperDeliverVO();
        vo.setDeliver(deliver);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @GetMapping("/api/v1/web_estudio/brand/mps_paper/{paper_id}/deliver")
    public StudioFetchMpsPaperDeliverVO fetchBrandMpsPaperDeliver(@PathVariable("paper_id") String paperId){
        StudioFetchMpsPaperDeliver deliver = studioCommercialPaperDeliverService.findBrandMpsPaperDeliver(paperId);
                StudioFetchMpsPaperDeliverVO vo = new StudioFetchMpsPaperDeliverVO();
        vo.setDeliver(deliver);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @PutMapping("/api/v1/web_estudio/brand/paper_deliver/leave_a_message")
    public SuccessVO leaveMessage(@RequestBody @Validated StudioMpsPaperDeliverLeaveMsgDTO dto){

        studioCommercialPaperDeliverService.leaveMessage(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @PutMapping("/api/v1/web_estudio/mps_paper_deliver/tag")
    public SuccessVO tagPaperDeliver(@RequestBody @Validated StudioPutMpsPaperDeliverTagDTO dto){
        boolean isReceiver =studioApiAccessControlService.isMpsPaperDeliverReceiver(dto.getDeliverId());
        // if tag to revision
        if(isReceiver && CommercialPaperDeliverTagEnum.REVISION.getMark().equals(dto.getTag())){
            studioCommercialPaperDeliverService.modifyPaperDeliverTag(dto);
        }
        // if tag to delivered
        if(isReceiver && CommercialPaperDeliverTagEnum.DELIVERED.getMark().equals(dto.getTag())){
            try {
                orderFlowService.insertOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                        TransTypeEnum.MPS_FUND_TRANSFER.getMark());
                // pay mps paper
                studioMpsFundService.payMpsPaperFee(dto);
                // update tag
                studioCommercialPaperDeliverService.modifyPaperDeliverTag(dto);
            }finally {
                orderFlowService.deleteOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                        TransTypeEnum.MPS_FUND_TRANSFER.getMark());
            }

        }
        return new SuccessVO(CodeEnum.SUCCESS);
    }
}
