package com.norm.timemall.app.ms.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.ms.domain.dto.MsReadMpsMsgDTO;
import com.norm.timemall.app.ms.domain.pojo.MsHaveNewMpsMsg;
import com.norm.timemall.app.ms.domain.vo.MsHaveNewMpsMsgVO;
import com.norm.timemall.app.ms.service.MsMpsMsgHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MsMpsMsgHelperController {
    @Autowired
    private MsMpsMsgHelperService msMpsMsgHelperService;
    @PutMapping("/api/v1/ms/mps_msg/read")
    public SuccessVO readMpsMsg(@RequestBody @Validated MsReadMpsMsgDTO dto){

        msMpsMsgHelperService.readMpsMsg(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @GetMapping("/api/v1/ms/mps_msg/has_new_msg")
    public MsHaveNewMpsMsgVO haveNewMpsMsg(String rooms){

        MsHaveNewMpsMsg ids=msMpsMsgHelperService.haveNewMpsMsg(rooms);
        MsHaveNewMpsMsgVO vo=new MsHaveNewMpsMsgVO();
        vo.setIds(ids);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
}
