package com.norm.timemall.app.mall.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.mall.domain.dto.ComputeIndDTO;
import com.norm.timemall.app.mall.service.DataListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataListController {
    @Autowired
    private DataListService dataListService;

    @PutMapping("/api/v1/web_mall/me/compute_ind")
    public SuccessVO computeInd(@RequestBody ComputeIndDTO dto){

        dataListService.callModel(dto.getModel());
        return  new SuccessVO(CodeEnum.SUCCESS);

    }
}
