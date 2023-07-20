package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.studio.domain.dto.StudioPutCellPlanDTO;
import com.norm.timemall.app.studio.service.StudioApiAccessControlService;
import com.norm.timemall.app.studio.service.StudioCellPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudioCellPlanController {
    @Autowired
    private StudioCellPlanService studioCellplanService;
    @Autowired
    private StudioApiAccessControlService studioApiAccessControlService;
    @ResponseBody
    @PutMapping(value = "/api/v1/web_estudio/cell/plan")
    public SuccessVO putCellPlan(@RequestBody @Validated StudioPutCellPlanDTO dto){

        // validate cell edit option
        boolean support=studioApiAccessControlService.cellSupportCurrentBrandModify(dto.getCellId());

        if(support){
            studioCellplanService.configCellPlan(dto);
        }else {
            throw  new ErrorCodeException(CodeEnum.INVALID_TOKEN);
        }

        return new SuccessVO(CodeEnum.SUCCESS);

    }
}
