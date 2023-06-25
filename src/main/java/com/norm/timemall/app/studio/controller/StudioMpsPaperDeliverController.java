package com.norm.timemall.app.studio.controller;

import cn.hutool.core.util.StrUtil;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.studio.domain.dto.StudioMpsPaperDeliverLeaveMsgDTO;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsPaperDeliver;
import com.norm.timemall.app.studio.domain.vo.StudioFetchMpsPaperDeliverVO;
import com.norm.timemall.app.studio.service.StudioCommercialPaperDeliverService;
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
        studioCommercialPaperDeliverService.newDeliver(paperId,previewUri,deliverUri);
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
    @PutMapping("/api/v1/web_estudio/brand/paper_deliver/leave_a_message")
    public SuccessVO leaveMessage(@RequestBody @Validated StudioMpsPaperDeliverLeaveMsgDTO dto){

        studioCommercialPaperDeliverService.leaveMessage(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
}