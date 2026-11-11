package com.norm.timemall.app.base.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.pojo.ro.FindElectricityHistoryPageRO;
import com.norm.timemall.app.base.pojo.vo.FindElectricityHistoryPageVO;
import com.norm.timemall.app.base.service.BaseElectricityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ElectricityController {
    @Autowired
    private BaseElectricityService baseElectricityService;

    @GetMapping("/api/v1/base/electricity/history")
    public FindElectricityHistoryPageVO findElectricityHistory(@Validated PageDTO dto) {
        IPage<FindElectricityHistoryPageRO> trans = baseElectricityService.findElectricityHistory(dto);
        FindElectricityHistoryPageVO vo = new FindElectricityHistoryPageVO();
        vo.setTrans(trans);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
}
