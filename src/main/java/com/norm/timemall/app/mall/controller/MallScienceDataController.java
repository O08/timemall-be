package com.norm.timemall.app.mall.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.mall.domain.dto.ScienceSemiDataDTO;
import com.norm.timemall.app.mall.service.MallScienceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MallScienceDataController {
    @Autowired
    private MallScienceDataService mallScienceDataService;
    @PostMapping("/api/v1/web_mall/science")
    public SuccessVO scienceSemiData( @RequestBody  @Validated ScienceSemiDataDTO dto){

        mallScienceDataService.addNewScienceSemiData(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
}
