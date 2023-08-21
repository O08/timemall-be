package com.norm.timemall.app.team.controller;

import cn.hutool.core.util.StrUtil;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.team.domain.dto.TeamDeliverLeaveMsgDTO;
import com.norm.timemall.app.team.domain.dto.TeamPutCommissionDeliverTagDTO;
import com.norm.timemall.app.team.domain.pojo.TeamFetchCommissionDeliver;
import com.norm.timemall.app.team.domain.vo.TeamFetchCommissionDeliverVO;
import com.norm.timemall.app.team.service.TeamApiAccessControlService;
import com.norm.timemall.app.team.service.TeamCommissionDeliverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TeamCommissionDeliverController {
    @Autowired
    private TeamCommissionDeliverService teamCommissionDeliverService;
    @Autowired
    private TeamApiAccessControlService teamApiAccessControlService;
    @Autowired
    private FileStoreService fileStoreService;
    @PostMapping("/api/v1/team/commission_ws/new_deliver")
    public SuccessVO addCommissionDeliver(@RequestParam("preview") MultipartFile preview,
                                        @RequestParam("deliver") MultipartFile deliver,
                                        @RequestParam("commissionId") String commissionId
    ){

        // validate
        if(preview.isEmpty()|| deliver.isEmpty()|| StrUtil.isBlank(commissionId)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        String role=teamApiAccessControlService.findCommissionWsRole(commissionId);
        if(!CommissionWsRoleEnum.SUPPLIER.getMark().equals(role)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // store file in classified
        String deliverUri = fileStoreService.storeWithLimitedAccess(deliver, FileStoreDir.COMMISSION_DELIVER);
        String previewUri = fileStoreService.storeWithLimitedAccess(preview, FileStoreDir.COMMISSION_PREVIEW);
        String previewName=preview.getOriginalFilename();
        String deliverName=deliver.getOriginalFilename();
        teamCommissionDeliverService.newDeliver(commissionId,previewUri,deliverUri,previewName,deliverName);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @GetMapping("/api/v1/team/commission_ws/{commission_id}/deliver")
    public TeamFetchCommissionDeliverVO fetchCommissionDeliver(@PathVariable("commission_id") String commissionId){

        String role=teamApiAccessControlService.findCommissionWsRole(commissionId);
        TeamFetchCommissionDeliver deliver = teamCommissionDeliverService.findCommissionDeliver(role,commissionId);
        TeamFetchCommissionDeliverVO vo = new TeamFetchCommissionDeliverVO();
        vo.setDeliver(deliver);
        vo.setResponseCode(CodeEnum.SUCCESS);

        return vo;
    }
    @PutMapping("/api/v1/team/commission_ws/deliver/leave_a_message")
    public SuccessVO leaveMessage(@RequestBody @Validated TeamDeliverLeaveMsgDTO dto){
        String role=teamApiAccessControlService.findCommissionWsRole(dto.getCommissionId());
        if(!CommissionWsRoleEnum.ADMIN.getMark().equals(role)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        teamCommissionDeliverService.leaveMessage(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @PutMapping("/api/v1/team/commission_ws/deliver/tag")
    public SuccessVO tagCommissionDeliver(@RequestBody @Validated TeamPutCommissionDeliverTagDTO dto){

        String role=teamApiAccessControlService.findCommissionWsRole(dto.getCommissionId());
        if(!CommissionWsRoleEnum.ADMIN.getMark().equals(role)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // if tag to revision
        if(DeliverTagEnum.REVISION.getMark().equals(dto.getTag())){
            teamCommissionDeliverService.modifyCommissionDeliverTag(dto);
        }

        return new SuccessVO(CodeEnum.SUCCESS);
    }
}
